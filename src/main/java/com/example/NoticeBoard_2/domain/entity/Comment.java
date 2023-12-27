package com.example.NoticeBoard_2.domain.entity;

import com.example.NoticeBoard_2.common.TimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Comment extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    private String content; //댓글 본문 내용

    /**
     * user가 여러개의 댓글을 작성할 수 있으므로 다대일 관계 매핑
     *
     * 댓글이 달린 게시판하고는... 다대일 관계 매핑
     *
     * */
    public void updateComment(String newBody){ //댓글 수정
        this.content = newBody;
    }

    //사용자 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    //게시글 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;


}
