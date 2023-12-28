package com.example.NoticeBoard_2.domain.dto.response.board;


import com.example.NoticeBoard_2.domain.entity.Board;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardResponseListDto {


    /** 카테고리별 게시글 목록 확인 */
    private Long boardId;
    private String title;
    private String content;
    private int recommendCnt; //추천 수 카운트 현황
    private int commentCnt;
    private String createDate; // 글 작성 일
    private String writerName; // 글쓴이

    @Builder
    public BoardResponseListDto(Long boardId,String title, String content, int recommendCnt, int commentCnt, String createDate, String writerName) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.recommendCnt = recommendCnt;
        this.commentCnt = commentCnt;
        this.createDate = createDate;
        this.writerName = writerName;
    }

    // db에서 board list를 가져오면 map함수로 entity -> dto로 변환작업 진행
    public static BoardResponseListDto fromEntity(Board board){
        return BoardResponseListDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .recommendCnt(board.getRecommendCnt())
                .commentCnt(board.getCommentCnt())
                .createDate(board.getCreatedDate())
                .writerName(board.getMember().getNickname())
                .build();

    }


}
