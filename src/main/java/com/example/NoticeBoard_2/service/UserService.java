package com.example.NoticeBoard_2.service;

import com.example.NoticeBoard_2.common.ApiResponse;
import com.example.NoticeBoard_2.domain.dto.UserLoginRequest;
import com.example.NoticeBoard_2.domain.entity.User;
import com.example.NoticeBoard_2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public ApiResponse checkSignUpNickname(String singUpNickname){
        boolean res = !userRepository.existsByNickname(singUpNickname);
        // 존재하면 0, 가능하면 1리턴
        if(res){
            return ApiResponse.success("OK");
        }
        else {
            return ApiResponse.fail("NotOK");
        }
    }

    public ApiResponse checkSignUpId(String signUpId){
        boolean res = !userRepository.existsByLoginId(signUpId);
        // 존재하면 0, 가능하면 1리턴
        if(res){
            return ApiResponse.success("OK");
        }
        else {
            return ApiResponse.fail("NotOK");
        }
    }

    public ApiResponse signup(User user){ //회원가입
        User save = userRepository.save(user);
        return ApiResponse.success("signupSuccess");
    }

    public ApiResponse login(UserLoginRequest userLoginRequest){
        Optional<User> optionalUser = userRepository.findByLoginId(userLoginRequest.getLoginId());
        if(optionalUser.isEmpty()){ //아이디가 존재하지 않음
            return ApiResponse.fail("로그인 정보가 틀렸습니다.");
        }
        User user = optionalUser.get();
        if(!user.getPassword().equals(userLoginRequest.getPassword())){ //비밀번호 불일치
            return ApiResponse.fail("로그인 정보가 틀렸습니다.");
        }
        return ApiResponse.success("로그인성공");

    }
}
