package com.example.NoticeBoard_2.controller;

import com.example.NoticeBoard_2.common.ApiResponse;
import com.example.NoticeBoard_2.domain.dto.UserSignupRequest;
import com.example.NoticeBoard_2.domain.entity.User;
import com.example.NoticeBoard_2.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /** 회원가입 절차*/
    @GetMapping("/checkNickname") //닉네임 중복검사
    public ApiResponse checkNickname(@RequestParam("nickname") String nickname){
        return userService.checkSignUpNickname(nickname);
    }

    @GetMapping("/checkId") // 아이디 중복검사
    public ApiResponse checkSignupId(@RequestParam("signupId") String signUpId){
        return userService.checkSignUpId(signUpId);
    }

    @PostMapping("/signup") //회원가입 버튼 누를 때
    public ApiResponse signup(@RequestBody UserSignupRequest userSignupRequest){
        User user = userSignupRequest.ToEntity();
        return userService.signup(user);
    }
}
