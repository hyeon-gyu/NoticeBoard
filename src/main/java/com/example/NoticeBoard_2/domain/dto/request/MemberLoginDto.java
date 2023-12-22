package com.example.NoticeBoard_2.domain.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberLoginDto {
    private String loginId;
    private String password;

    @Builder
    public MemberLoginDto(String loginId, String password){
        this.loginId = loginId;
        this.password = password;
    }
}
