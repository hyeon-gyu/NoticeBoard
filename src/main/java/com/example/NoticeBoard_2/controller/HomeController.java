package com.example.NoticeBoard_2.controller;

import com.example.NoticeBoard_2.common.ApiResponse;
import com.example.NoticeBoard_2.domain.dto.UserLoginRequest;
import com.example.NoticeBoard_2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final UserService userService;

    /** 로그인 절차 */
    @PostMapping("/login")
    public ApiResponse login(@RequestBody UserLoginRequest userLoginRequest){
        return userService.login(userLoginRequest);
    }



}
