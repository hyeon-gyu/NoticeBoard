package com.example.NoticeBoard_2.domain.dto.response.member;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
public class MemberTokenDto {

    private String nickname;
    private String token;


    public MemberTokenDto(String nickname, String token) {
        this.nickname = nickname;
        this.token = token;
    }

    // ENTITY -> DTO
    public static MemberTokenDto fromEntity(UserDetails member, String token){
        return MemberTokenDto.builder()
                .nickname(member.getUsername())
                .token(token)
                .build();
    }


}
