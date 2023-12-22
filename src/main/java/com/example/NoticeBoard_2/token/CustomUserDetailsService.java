package com.example.NoticeBoard_2.token;

import com.example.NoticeBoard_2.common.ResourceNotFoundException;
import com.example.NoticeBoard_2.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loging loadUserByUsername : " + username);
        return this.memberRepository.findByLoginId(username).orElseThrow(
                () -> new ResourceNotFoundException("Member" , "Member LoginId: ", username));

        /** 리턴과 동시에 시큐리티 세션에 유저 정보가 저장된다.*/
    }


}
