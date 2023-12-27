package com.example.NoticeBoard_2.service;

import com.example.NoticeBoard_2.common.MemberException;
import com.example.NoticeBoard_2.common.ResourceNotFoundException;
import com.example.NoticeBoard_2.domain.dto.request.member.MemberLoginDto;
import com.example.NoticeBoard_2.domain.dto.request.member.MemberSignupDto;
import com.example.NoticeBoard_2.domain.dto.response.board.BoardResponseDetailDto;
import com.example.NoticeBoard_2.domain.dto.response.member.MemberResponseCntDto;
import com.example.NoticeBoard_2.domain.dto.response.member.MemberResponseDto;
import com.example.NoticeBoard_2.domain.dto.response.member.MemberTokenDto;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.domain.enum_class.MemberRole;
import com.example.NoticeBoard_2.repository.MemberRepository;
import com.example.NoticeBoard_2.token.CustomUserDetailsService;
import com.example.NoticeBoard_2.token.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MemberService {

    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    //닉네임 중복 확인
    public HttpStatus checkSignUpNickname(String singUpNickname){
        isExistUsername(singUpNickname);
        return HttpStatus.OK;
    }
    //아이디 중복 확인
    public HttpStatus checkSignUpLoginId(String signupId){
        isExistSignupId(signupId);
        return HttpStatus.OK;
    }

    // 닉네임 중복 검사 (DB 확인)
    private void isExistUsername(String nickname){
        if(memberRepository.findByNickname(nickname).isPresent()){
            //존재하면 1, 존재하지 않으면 0 -> 존재하면 exception 터트리기
            throw new MemberException("이미 사용중인 닉네임입니다.", HttpStatus.BAD_REQUEST);
        }
    }
    // 아이디 중복체크 (DB 확인)
    private void isExistSignupId(String signupId){
        if(memberRepository.findByLoginId(signupId).isPresent()){
            //존재하면 1, 존재하지 않으면 0 -> 존재하면 exception 터트리기
            throw new MemberException("이미 사용중인 아이디입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    //회원가입
    public MemberResponseDto signUp(MemberSignupDto dto){
        //비밀번호 암호화 작업
        String encodePwd = encoder.encode(dto.getPassword());
        dto.setPassword(encodePwd);

        Member saveMember = memberRepository.save(MemberSignupDto.ofEntity(dto));
        return MemberResponseDto.fromEntity(saveMember);
    }

    // 로그인
    public MemberTokenDto login(MemberLoginDto memberLoginDto){
        authenticate(memberLoginDto.getLoginId(), memberLoginDto.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberLoginDto.getLoginId());
        checkEncodePassword(memberLoginDto.getPassword(), userDetails.getPassword());
        String token = jwtTokenUtil.generateToken(userDetails);
        return MemberTokenDto.fromEntity(userDetails, token);
    }

    /**
     * 사용자 인증
     * @param LoginId
     * @param password
     */
    private void authenticate(String LoginId, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(LoginId, password));
        } catch (DisabledException e) {
            throw new MemberException("인증되지 않은 아이디입니다.", HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e) {
            throw new MemberException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    private void checkEncodePassword(String rawPassword, String encodedPassword) {
        if (!encoder.matches(rawPassword, encodedPassword)) {
            throw new MemberException("패스워드 불일치", HttpStatus.BAD_REQUEST);
        }
    }

    // 회원탈퇴
    public MemberResponseDto withdraw(Member member){
        Member findMember = memberRepository.findByLoginId(member.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member LoginId", member.getLoginId()));
        // 삭제 순서 : 추천 -> 댓글 -> 게시글 -> 멤버
        memberRepository.delete(findMember);
        return MemberResponseDto.fromEntity(findMember);
    }

    // 총 회원 수 조회
    public List<MemberResponseCntDto> countMember(){
        List<Object[]> memberCntLists = memberRepository.countMemberByMemberRole();
        Map<MemberRole, Long> countMap = new EnumMap<>(MemberRole.class);

        for (MemberRole role: MemberRole.values()){
            if(!role.equals(MemberRole.ADMIN)){
                countMap.put(role,0L);
            }
        }
        for (Object[] index : memberCntLists) {
            countMap.put((MemberRole)index[0], (Long)index[1]);
        }
        return MemberResponseCntDto.fromEntity(countMap);
    }


}
