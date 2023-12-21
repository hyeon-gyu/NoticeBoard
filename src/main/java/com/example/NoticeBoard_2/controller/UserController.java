package com.example.NoticeBoard_2.controller;

import com.example.NoticeBoard_2.domain.dto.request.UserSignupDto;
import com.example.NoticeBoard_2.domain.dto.response.UserResponseDto;
import com.example.NoticeBoard_2.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /** 회원가입 절차*/
    @GetMapping("/checkNickname") //닉네임 중복검사
    public ResponseEntity<?> checkDupNickname(@RequestParam("nickname") String nickname){
        userService.checkSignUpNickname(nickname);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/checkId") // 아이디 중복검사
    public ResponseEntity<?> checkDupId(@RequestParam("signupId") String signUpId){
        userService.checkSignUpId(signUpId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/signup") //회원가입 버튼 누를 때
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserSignupDto userSignupDto){
        UserResponseDto signupMember = userService.signUp(userSignupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(signupMember);
    }

    //회원정보 수정 메소드 만들기!
}
