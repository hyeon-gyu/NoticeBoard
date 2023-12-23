package com.example.NoticeBoard_2.domain.dto.response;


import com.example.NoticeBoard_2.domain.entity.Board;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class BoardResponseWriteDto {

    private Long boardId;
    private String title;
    private String content;
    private String writerName;
    private String createdDate;

    public BoardResponseWriteDto(Long boardId, String title, String content, String writerName, String createdDate) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.createdDate = createdDate;
    }


    public static BoardResponseWriteDto fromEntity(Board board, String writerName) {
        return BoardResponseWriteDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getBody())
                .writerName(writerName)
                .createdDate(board.getCreatedDate())
                .build();
    }
}
