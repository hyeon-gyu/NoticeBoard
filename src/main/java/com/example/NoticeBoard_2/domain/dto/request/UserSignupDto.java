package com.example.NoticeBoard_2.domain.dto.request;

import com.example.NoticeBoard_2.domain.entity.User;
import com.example.NoticeBoard_2.domain.enum_class.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserSignupDto {

    private String loginId;
    private String password;
    private String nickname;



    //DTO -> ENTITY
    public static User ofEntity(UserSignupDto dto){
        return User.builder()
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .userRole(UserRole.ASSOCIATE)
                .signUpAt(LocalDateTime.now())
                .recommendCnt(0)
                .build();
    }

}
