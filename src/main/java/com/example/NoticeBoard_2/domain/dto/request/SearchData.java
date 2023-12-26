package com.example.NoticeBoard_2.domain.dto.request;


import com.example.NoticeBoard_2.domain.dto.response.BoardResponseListDto;
import com.example.NoticeBoard_2.domain.entity.Board;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SearchData {  // LIKE {} 로 검색할 예정
    private String title;
    private String writer;
    private String body;

    @Builder
    public SearchData(String title, String writer, String body) {
        this.title = title;
        this.writer = writer;
        this.body = body;
    }

    public static SearchData createSearchData(String title, String writer, String body){
        return SearchData.builder()
                .title(title)
                .writer(writer)
                .body(body)
                .build();
    }
}
