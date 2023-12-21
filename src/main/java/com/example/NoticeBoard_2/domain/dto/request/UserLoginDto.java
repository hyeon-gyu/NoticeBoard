package com.example.NoticeBoard_2.domain.dto.request;

import lombok.Data;

@Data
public class UserLoginDto {
    private String loginId;
    private String password;
}
