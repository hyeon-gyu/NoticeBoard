package com.example.NoticeBoard_2.token;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /** 인증되지 않은 사용자가 보호된 자원에 접근하려고 할 때 커스텀 응답 제공 역할
     *
     *
     * 사용자가 인증(로그인)되지 않은 상태에서 보호된 자원에 접근하려고 할 때, 혹은 사용자의 인증이 유효하지 않거나 만료된 경우에 동작
     * */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
