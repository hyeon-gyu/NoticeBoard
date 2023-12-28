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
@NoArgsConstructor
@Getter
public class Recommend extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 하나의 게시글에 여러 추천이 가능하므로 다대일 관계
     * 한 멤버가 여러 추천을 누를 수 있으므로 다대일 관계
     */

    // 추천과 게시글 관계 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    // 추천과 사용자 관계 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Builder
    public Recommend(Board board, Member member) {
        this.board = board;
        this.member = member;
    }

}

