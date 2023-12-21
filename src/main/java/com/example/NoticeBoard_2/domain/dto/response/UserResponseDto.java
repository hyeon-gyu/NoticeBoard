package com.example.NoticeBoard_2.domain.dto.response;

import com.example.NoticeBoard_2.domain.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

    private final String user_id;
    private final String nickname;


    // ENTITY -> DTO
    public static UserResponseDto fromEntity(User user){
        return UserResponseDto.builder()
                .user_id(user.getLoginId())
                .nickname(user.getNickname())
                .build();
    }
}
