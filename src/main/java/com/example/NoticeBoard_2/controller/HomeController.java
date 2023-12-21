package com.example.NoticeBoard_2.controller;

import com.example.NoticeBoard_2.domain.dto.ApiResponse;
import com.example.NoticeBoard_2.domain.dto.request.UserLoginDto;
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
    public ApiResponse login(@RequestBody UserLoginDto userLoginDto){
        return userService.login(userLoginDto);
    }



}
