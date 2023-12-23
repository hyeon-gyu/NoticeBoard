package com.example.NoticeBoard_2;

import com.example.NoticeBoard_2.domain.dto.request.MemberLoginDto;
import com.example.NoticeBoard_2.domain.dto.request.MemberSignupDto;
import com.example.NoticeBoard_2.domain.dto.response.MemberResponseDto;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardApplicationTests {


	@Autowired
	private MemberService memberService;

	@Test
	public void testMemberSignup(){

		MemberSignupDto signupDto = new MemberSignupDto("lim","1111","gyugyu");
		MemberResponseDto memberResponseDto = memberService.signUp(signupDto);
		Assertions.assertNotNull(memberResponseDto);
	}

}
