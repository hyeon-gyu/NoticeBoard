package com.example.NoticeBoard_2.domain.dto;


import com.example.NoticeBoard_2.domain.entity.Board;
import com.example.NoticeBoard_2.domain.entity.User;
import com.example.NoticeBoard_2.domain.enum_class.BoardCategory;
import lombok.Data;

@Data
public class WriteBoardRequest {

    private String title;
    private String body;

    public Board ToEntity(BoardCategory boardCategory, User user){
        return Board.builder()
                .user(user)
                .title(title)
                .body(body)
                .boardCategory(boardCategory)
                .recommendCnt(0)
                .commentCnt(0)
                .build();
    }
}
