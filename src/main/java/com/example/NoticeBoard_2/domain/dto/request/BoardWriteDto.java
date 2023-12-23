package com.example.NoticeBoard_2.domain.dto.request;

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
    public static Board ofEntity(BoardWriteDto boardWriteDto){
        return Board.builder()
                .title(boardWriteDto.title)
                .body(boardWriteDto.content)
                .build();
    }

    public static Board applyCategory(BoardWriteDto boardWriteDto, String category){
        Board board = BoardWriteDto.ofEntity(boardWriteDto);
        board.setBoardCategory(category);
        return board;
    }
}
