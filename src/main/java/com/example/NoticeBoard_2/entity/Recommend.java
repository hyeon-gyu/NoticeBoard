package com.example.NoticeBoard_2.entity;

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
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 추천은 여러 사람들에게 받을 수 있는데.. 추천과 게시글간의 관계는 다대일
     * 추천한 유저와 추천 간의 관계는 뭐지? 다대다는 쪼개야하는데.. 다대일?
     * */

    // 추천과 게시글 관계 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    // 추천과 사용자 관계 다대일로 추정중..
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}

