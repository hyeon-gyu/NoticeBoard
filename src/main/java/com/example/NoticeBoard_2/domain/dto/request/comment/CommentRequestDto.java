package com.example.NoticeBoard_2.domain.dto.request.comment;


import com.example.NoticeBoard_2.domain.dto.response.comment.CommentResponseDto;
import com.example.NoticeBoard_2.domain.entity.Comment;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequestDto {

    // security가 누가 작성했는지 username으로 memberId 선별과정 진행
    private String content;

    @Builder
    public CommentRequestDto(String content) {
        this.content = content;
    }

    // request body로 넘어온 dto를 comment 엔티티로 변환작업
    public static Comment ofEntity(CommentRequestDto dto){
        return Comment.builder()
                .content(dto.getContent())
                .build();
    }

}
