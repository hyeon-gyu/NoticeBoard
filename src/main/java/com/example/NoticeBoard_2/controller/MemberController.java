package com.example.NoticeBoard_2.controller;

import com.example.NoticeBoard_2.domain.dto.request.member.MemberSignupDto;
import com.example.NoticeBoard_2.domain.dto.response.member.MemberResponseCntDto;
import com.example.NoticeBoard_2.domain.dto.response.member.MemberResponseDto;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
//@SecurityRequirement(name = "bearerAuth")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 절차
     */
    @GetMapping("/checkNickname") //닉네임 중복검사
    public ResponseEntity<?> checkDupNickname(@RequestParam("nickname") String nickname) {
        memberService.checkSignUpNickname(nickname);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/checkId") // 아이디 중복검사
    public ResponseEntity<?> checkDupLoginId(@RequestParam("signupId") String signUpLoginId) {
        memberService.checkSignUpLoginId(signUpLoginId);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @PostMapping("/signup") //회원가입 버튼 누를 때
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberSignupDto memberSignupDto) {
        MemberResponseDto signupMember = memberService.signUp(memberSignupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(signupMember);
    }

    @GetMapping("/withdraw") //회원 탈퇴 (토큰으로 사용자 검증작업)
    public ResponseEntity<MemberResponseDto> withdraw(@AuthenticationPrincipal Member member) {
        MemberResponseDto withdrawMember = memberService.withdraw(member);
        return ResponseEntity.status(HttpStatus.OK).body(withdrawMember);
    }

    //총 회원 수 조회 (등급 별 분류하여 조회) : 등급 별 인원 수 카운트 하여 category, countNum만 넘기면 될듯
    @GetMapping("/count")
    public ResponseEntity<List<MemberResponseCntDto>> countMember() {
        List<MemberResponseCntDto> memberResponseCntDto = memberService.countMember();
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseCntDto);

    }


    //회원정보 수정 메소드 만들기!
}
