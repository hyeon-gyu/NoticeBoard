package com.example.NoticeBoard_2.domain.entity;


import com.example.NoticeBoard_2.common.TimeEntity;
import com.example.NoticeBoard_2.domain.enum_class.BoardCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter

public class Board extends TimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory; // 가입인사 게시판, 자유게시판, 관리자용 게시판(공지사항)

    //추천 수
    private int recommendCnt;
    //댓글 수
    private int commentCnt;

    @Builder
    public Board(String title, String content, BoardCategory boardCategory, Member member, int recommendCnt, int commentCnt){
        this.title = title;
        this.content = content;
        this.boardCategory = boardCategory;
        this.member = member;
        this.recommendCnt = 0;
        this.commentCnt = 0;
    }

    //유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    //댓글(일대다)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> commentList;

    //orphanRemoval = true VS cascade = CascadeType.REMOVE 비교 공부하기

    //추천(일대다)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recommend> recommendList;

    /**
    public void updateBoard(BoardDto dto){
     this.title = dto.getTitle();
     this.body = dto.getBody();
     }
     */

    /** 양방향 연관관계 매핑 작업*/
    public void setMappingMember(Member member){
        this.member = member;
        member.getBoards().add(this);
    }

    public void setBoardCategory(String category){ //대소문자 관계없이 category 선별 가능
        this.boardCategory = BoardCategory.of(category);
    }

}

