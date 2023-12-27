package com.example.NoticeBoard_2.domain.dto.response.member;

import com.example.NoticeBoard_2.domain.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberResponseDto {

    private String user_id;
    private String nickname;

    @Builder
    public MemberResponseDto(String user_id, String nickname) {
        this.user_id = user_id;
        this.nickname = nickname;
    }

    // ENTITY -> DTO
    public static MemberResponseDto fromEntity(Member member){
        return MemberResponseDto.builder()
                .user_id(member.getLoginId())
                .nickname(member.getNickname())
                .build();
    }
}
