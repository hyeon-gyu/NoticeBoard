package com.example.NoticeBoard_2.domain.dto.response.comment;


import com.example.NoticeBoard_2.domain.entity.Comment;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentResponseDto {


    private Long commentId;
    private String content;
    private String createdDate;
    private String modifiedDate;
    private String commentWriter; //댓글 작성자

    @Builder
    public CommentResponseDto(Long commentId, String content, String createdDate, String modifiedDate, String commentWriter) {
        this.commentId = commentId;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.commentWriter = commentWriter;
    }

    public static CommentResponseDto fromEntity(Comment comment){
        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .modifiedDate(comment.getModifiedDate())
                .commentWriter(comment.getMember().getNickname())
                .build();
    }




}
