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
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory; // 가입인사 게시판, 자유게시판, 관리자용 게시판(공지사항)

    /**
     * 사용자와 다대일 관계 매핑
     * 댓글과 일대다 관계 매핑
     *
     * */
}
