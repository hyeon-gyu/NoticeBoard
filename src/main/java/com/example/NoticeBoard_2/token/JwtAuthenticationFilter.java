package com.example.NoticeBoard_2.token;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;


@Component  //컴포넌트 스캔의 대상으로 지정
@RequiredArgsConstructor
@Slf4j //로거 제공 LOMBOK
@PropertySource("classpath:jwt.yml")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /** HTTP 요청을 가로채고 유효한 JWT를 포함하고 있는지 확인하는 역할
     * 유효한 JWT 발견되면 해당 토큰과 관련된 사용자 인증 진행
     * 사실상 여기가 토큰 확인하는 시작시점
     */

    private final UserDetailsService userDetailsService; //사용자 세부정보 검색하는 SpringSecurity 서비스
    private final JwtTokenUtil jwtTokenUtil; //사용자 이름 추출, 토큰 유효성 검사 진행하는 객체

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Thread currentThread = Thread.currentThread();
        //log.info("현재 실행 중인 스레드: " + currentThread.getName());

        // 모든 헤더 이름과 값 출력
//        Collections.list(request.getHeaderNames())
//                .forEach(headerName -> log.info("Header: " + headerName + " Value: " + request.getHeader(headerName)));
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String username = null;
        String authToken = null;

        String requestURI = request.getRequestURI();
        if (requestURI.contains("/user/checkNickname") // 사용자 인가 없이 허용되는 url모음
            || requestURI.contains("/user/checkId")
            || requestURI.contains("/user/signup")
            || requestURI.contains(("login"))
            || requestURI.contains("/board/search")
            || requestURI.contains("/board/detail")
            || requestURI.contains("/user/count")
            || requestURI.contains("/board/sort/")
            || requestURI.contains("/board/list/")
        ){
            filterChain.doFilter(request,response);
            return;
        }
        log.info(header);
        //'Bearer '로 시작하는 토큰 정보 꺼내기
        if (header != null && header.startsWith("Bearer ")) {
            authToken = header.replace("Bearer ","");
            try {
                username = this.jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException ex) {     // 토큰 내 사용자 정보 오류
                log.info("fail get user id");
                ex.printStackTrace();
            } catch (ExpiredJwtException ex) { //토큰이 만료되었을 때
                log.info("Token expired");
                ex.printStackTrace();
            } catch (MalformedJwtException ex) { //토큰 형성이 올바르지 않을 때
                log.info("Invalid JWT !!");
                System.out.println();
                ex.printStackTrace();
            } catch (Exception e) {
                log.info("Unable to get JWT Token !!");
                e.getStackTrace();
            }

        } else {
            log.info("JWT does not begin with Bearer !!");
        }

        //사용자 인증 : 토큰이 유효하다면 사용자 세부정보 로드하여 사용자 인증과정 진행
        if ((username != null) && (SecurityContextHolder.getContext().getAuthentication() == null)) {
            //log.info(SecurityContextHolder.getContext().getAuthentication().getName());
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtTokenUtil.validateToken(authToken, userDetails)) {

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //
                authenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } else {
                log.info("Invalid JWT Token !!");
            }
        } else {
            log.info("Username is null or context is not null !!");
        }
        filterChain.doFilter(request, response);
    }
}

/**
 * 1. 요청이 도착하면 필터는 요청 헤더에서 JWT를 확인합니다.
 * 2. JWT가 발견되고 유효하면 UserDetailsService에서 로드한 사용자 세부 정보에 대해 토큰을 검증하여 사용자를 인증합니다.
 * 3. 토큰이 유효하면 해당 요청에 대해 사용자가 인증된 것으로 간주되며, 사용자의 권한에 따라 Spring Security가 접근을 허용합니다.
 * 4. 토큰이 없거나 유효하지 않은 경우, 인증 없이 요청이 진행됩니다(보안 구성에 따라 접근이 거부될 수 있습니다).*/
