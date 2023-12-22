package com.example.NoticeBoard_2.controller;

import com.example.NoticeBoard_2.domain.dto.request.MemberSignupDto;
import com.example.NoticeBoard_2.domain.dto.response.MemberResponseDto;
import com.example.NoticeBoard_2.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;

    /** 회원가입 절차*/
    @GetMapping("/checkNickname") //닉네임 중복검사
    public ResponseEntity<?> checkDupNickname(@RequestParam("nickname") String nickname){
        memberService.checkSignUpNickname(nickname);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/checkId") // 아이디 중복검사
    public ResponseEntity<?> checkDupLoginId(@RequestParam("signupId") String signUpLoginId){
        memberService.checkSignUpLoginId(signUpLoginId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/signup") //회원가입 버튼 누를 때
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberSignupDto memberSignupDto){
        MemberResponseDto signupMember = memberService.signUp(memberSignupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(signupMember);
    }

    //회원정보 수정 메소드 만들기!
}
