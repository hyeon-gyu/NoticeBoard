package com.example.NoticeBoard_2.domain.dto;

import com.example.NoticeBoard_2.domain.entity.User;
import com.example.NoticeBoard_2.domain.enum_class.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserSignupRequest {

    private String loginId;
    private String password;
    private String nickname;

    public User ToEntity(){
        return User
                .builder()
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .userRole(UserRole.ASSOCIATE)
                .signUpAt(LocalDateTime.now())
                .recommendCnt(0)
                .build();
    }
}