package com.example.NoticeBoard_2.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment_body; //댓글 본문 내용

    /**
     * user가 여러개의 댓글을 작성할 수 있으므로 다대일 관계 매핑
     *
     * 댓글이 달린 게시판하고는... 다대일 관계 매핑
     *
     * */
    public void updateComment(String newBody){ //댓글 수정
        this.comment_body = newBody;
    }

    //사용자 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Member member;

    //게시글 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;


}
