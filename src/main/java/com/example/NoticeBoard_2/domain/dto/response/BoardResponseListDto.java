package com.example.NoticeBoard_2.domain.dto.response;


import com.example.NoticeBoard_2.domain.entity.Board;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class BoardResponseListDto {


    /** 카테고리별 게시글 목록 확인 */

    private String title;
    private String content;
    private int recommendCnt; //추천 수 카운트 현황
    private String createDate; // 글 작성 일
    private String writerName; // 글쓴이

    public BoardResponseListDto(String title, String content, int recommendCnt, String createDate, String writerName) {
        this.title = title;
        this.content = content;
        this.recommendCnt = recommendCnt;
        this.createDate = createDate;
        this.writerName = writerName;
    }

    // db에서 board list를 가져오면 map함수로 entity -> dto로 변환작업 진행
    public static BoardResponseListDto fromEntity(Board board){
        return BoardResponseListDto.builder()
                .title(board.getTitle())
                .content(board.getBody())
                .recommendCnt(board.getRecommendCnt())
                .createDate(board.getCreatedDate())
                .writerName(board.getMember().getUsername())
                .build();

    }


}
