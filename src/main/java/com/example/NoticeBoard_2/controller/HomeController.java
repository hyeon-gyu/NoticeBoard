package com.example.NoticeBoard_2.controller;

import com.example.NoticeBoard_2.domain.dto.request.member.MemberLoginDto;
import com.example.NoticeBoard_2.domain.dto.response.member.MemberTokenDto;
import com.example.NoticeBoard_2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final MemberService memberService;

    /** 로그인 절차 */
    @PostMapping("/login")
    public ResponseEntity<MemberTokenDto> login(@RequestBody MemberLoginDto memberLoginDto){
        MemberTokenDto memberTokenDto = memberService.login(memberLoginDto);
        return ResponseEntity.status(HttpStatus.OK).header(memberTokenDto.getToken()).body(memberTokenDto);
    }





}
