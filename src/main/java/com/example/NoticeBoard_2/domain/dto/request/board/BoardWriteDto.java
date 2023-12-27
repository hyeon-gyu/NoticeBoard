package com.example.NoticeBoard_2.domain.dto.request.board;

import com.example.NoticeBoard_2.domain.entity.Board;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardWriteDto {

    private String title;
    private String content;

    @Builder
    public BoardWriteDto(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public static Board ofEntity(BoardWriteDto boardWriteDto){
        return Board.builder()
                .title(boardWriteDto.title)
                .content(boardWriteDto.content)
                .commentCnt(0)
                .recommendCnt(0)
                .build();
    }

    public static Board applyCategory(BoardWriteDto boardWriteDto, String category){
        Board board = BoardWriteDto.ofEntity(boardWriteDto);
        board.setBoardCategory(category);
        return board;
    }
}
