package com.example.NoticeBoard_2.domain.dto.request.board;


import lombok.Builder;
import lombok.Data;

@Data
public class SearchData {  // LIKE {} 로 검색할 예정
    private String title;
    private String writer;
    private String content;

    @Builder
    public SearchData(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

    public static SearchData createSearchData(String title, String writer, String content){
        return SearchData.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .build();
    }
}
