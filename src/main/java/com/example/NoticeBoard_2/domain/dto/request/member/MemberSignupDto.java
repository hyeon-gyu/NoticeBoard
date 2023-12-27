package com.example.NoticeBoard_2.domain.dto.request.member;

import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.domain.enum_class.MemberRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberSignupDto {

    private String loginId;
    private String password;
    private String nickname;

    @Builder
    public MemberSignupDto(String loginId, String password, String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
    }

    //DTO -> ENTITY
    public static Member ofEntity(MemberSignupDto dto){
        return Member.builder()
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .memberRole(MemberRole.ASSOCIATE)
                .signUpAt(LocalDateTime.now())
                .recommendCnt(0)
                .build();
    }

}
