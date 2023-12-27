package com.example.NoticeBoard_2.domain.dto.response.board;


import com.example.NoticeBoard_2.domain.dto.response.comment.CommentResponseDto;
import com.example.NoticeBoard_2.domain.entity.Board;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class BoardResponseDetailDto {

    private Long board_id;
    private String title;
    private String content;
    private int commentCnt; //댓글 수
    private int recommendCnt; //추천 수
    private String createdDate;
    private String modifiedDate;
    private String writer;

    // 댓글 연관관계
    private List<CommentResponseDto> comments;

    @Builder
    public BoardResponseDetailDto(
            Long board_id, String title, String content, int commentCnt,
            int recommendCnt, String createdDate, String modifiedDate, String writer, List<CommentResponseDto> comments) {
        this.board_id = board_id;
        this.title = title;
        this.content = content;
        this.commentCnt = commentCnt;
        this.recommendCnt = recommendCnt;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.writer = writer;
        this.comments = comments;
    }

    public static BoardResponseDetailDto fromEntity(Board board){
        return BoardResponseDetailDto.builder()
                .board_id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getMember().getNickname())
                .commentCnt(board.getCommentCnt())
                .recommendCnt(board.getRecommendCnt())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .comments(board.getCommentList().stream()
                        .map(CommentResponseDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
