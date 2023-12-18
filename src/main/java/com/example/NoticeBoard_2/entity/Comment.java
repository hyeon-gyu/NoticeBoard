package com.example.NoticeBoard_2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
     * 유저와 다대일 관계 매핑
     *
     * 댓글이 달린 게시판하고는... 다대일인가?
     * */
    public void updateComment(String newBody){ //댓글 수정
        this.comment_body = newBody;
    }

}
