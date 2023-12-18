package com.example.NoticeBoard_2.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId; // 로그인할 때 사용할 아이디 정보
    private String password;
    private String nickname;
    private LocalDateTime signUpAt; // 회원가입 날짜
    private Integer recommendCnt; // 추천 수

    @Enumerated(EnumType.STRING) // 유저 등급 지정, 테이블에 string으로 값 들어감
    private UserRole userRole;

    public void editInfo(String newPassword, String newNickname){ // 개인정보 수정 (비밀번호, 닉네임)
        this.password = newPassword;
        this.nickname = newNickname;
    }

    /**
     * user가 작성한 글과 일대다 관계 매핑을 진행해야함
     * user가 추천을 누른 글과도 일대다 관계 매핑을 진행해야함
     * user가 작성한 댓글과도 일대다 관계 매핑을 진행해야함
     * 가입인사 게시판에서 글을 작성하면 준회원에서 정회원으로 등극 ASSOCIATE -> REGULAR (추후 다른사람글에 댓글 달아야 등업되는 조건 추가)
     * */




}
