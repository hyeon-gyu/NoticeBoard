package com.example.NoticeBoard_2.domain.entity;


import com.example.NoticeBoard_2.common.TimeEntity;
import com.example.NoticeBoard_2.domain.enum_class.BoardCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Board extends TimeEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;
    private String body;

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory; // 가입인사 게시판, 자유게시판, 관리자용 게시판(공지사항)

    /**
     * 한명의 user가 여러 글을 작성할 수 있으므로 board 클래스 관점에선 다대일로 매핑해야함
     * 하나의 게시글에 여러 댓글이 달릴 수 있으므로 일대다 관계 매핑
     * 하나의 게시글에 여러개의 추천이 달릴 수도 있으므로 일대다 관계 매핑
     *
     * */

    //유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    //댓글(일대다)
    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Comment> commentList;

    //추천(일대다)
    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Recommend> recommendList;

    /**
    public void updateBoard(BoardDto dto){
     this.title = dto.getTitle();
     this.body = dto.getBody();
     }
     */
    //추천 수
    private Integer recommendCnt;
    //댓글 수
    private Integer commentCnt;

    /** 양방향 연관관계 매핑 작업*/
    public void setMappingMember(Member member){
        this.member = member;
        member.getBoards().add(this);
    }

    public void setBoardCategory(String category){ //대소문자 관계없이 category 선별 가능
        this.boardCategory = BoardCategory.of(category);
    }

}

