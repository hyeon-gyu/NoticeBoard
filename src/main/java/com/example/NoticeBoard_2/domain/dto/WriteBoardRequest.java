package com.example.NoticeBoard_2.domain.dto;


import com.example.NoticeBoard_2.domain.entity.Board;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.domain.enum_class.BoardCategory;
import lombok.Data;

@Data
public class WriteBoardRequest {

    private String title;
    private String body;

    public Board ToEntity(BoardCategory boardCategory, Member member){
        return Board.builder()
                .member(member)
                .title(title)
                .body(body)
                .boardCategory(boardCategory)
                .recommendCnt(0)
                .commentCnt(0)
                .build();
    }
}
