package com.example.NoticeBoard_2.service;

import com.example.NoticeBoard_2.common.UserException;
import com.example.NoticeBoard_2.domain.dto.ApiResponse;
import com.example.NoticeBoard_2.domain.dto.request.UserLoginDto;
import com.example.NoticeBoard_2.domain.dto.request.UserSignupDto;
import com.example.NoticeBoard_2.domain.dto.response.UserResponseDto;
import com.example.NoticeBoard_2.domain.entity.User;
import com.example.NoticeBoard_2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    //닉네임 중복 확인
    public HttpStatus checkSignUpNickname(String singUpNickname){
        isExistNickname(singUpNickname);
        return HttpStatus.OK;
    }
    //아이디 중복 확인
    public HttpStatus checkSignUpId(String signupId){
        isExistSignupId(signupId);
        return HttpStatus.OK;
    }

    // 닉네임 중복 검사 (DB 확인)
    private void isExistNickname(String nickname){
        if(!userRepository.existsByNickname(nickname)){
            //존재하면 1, 존재하지 않으면 0 -> 존재하면 exception 터트리기
            throw new UserException(HttpStatus.BAD_REQUEST, "이미 사용중인 닉네임입니다.");
        }
    }
    // 아이디 중복체크 (DB 확인)
    private void isExistSignupId(String signupId){
        if(!userRepository.existsByLoginId(signupId)){
            //존재하면 1, 존재하지 않으면 0 -> 존재하면 exception 터트리기
            throw new UserException(HttpStatus.BAD_REQUEST, "이미 사용중인 아이디입니다.");
        }
    }

    //회원가입
    public UserResponseDto signUp(UserSignupDto dto){
        // 비밀번호 암호화는 나중에..
        User saveUser = userRepository.save(UserSignupDto.ofEntity(dto));
        return UserResponseDto.fromEntity(saveUser);
    }

    public ApiResponse login(UserLoginDto userLoginDto){
        Optional<User> optionalUser = userRepository.findByLoginId(userLoginDto.getLoginId());
        if(optionalUser.isEmpty()){ //아이디가 존재하지 않음
            return ApiResponse.fail("로그인 정보가 틀렸습니다.");
        }
        User user = optionalUser.get();
        if(!user.getPassword().equals(userLoginDto.getPassword())){ //비밀번호 불일치
            return ApiResponse.fail("로그인 정보가 틀렸습니다.");
        }
        return ApiResponse.success("로그인성공");

    }
}
