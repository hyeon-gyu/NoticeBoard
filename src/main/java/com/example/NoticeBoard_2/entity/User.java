package com.example.NoticeBoard_2.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private Integer recommendCnt; // 총 추천받은 수

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
     *
     * user가 작성한 게시글 목록을 알아야함
     *
     * */

    /** 외래키를 가진 board class가 관계의 주인이 되는 것이 좋다
     * => 연관관계의 주인은 외래키를 가진 객체가 되는 것이 바람직. 보통 일대다에서 다 위치하는 객체가 관계의 주인이다.
     * 유저가 게시글을 무엇을 작성했는지도 확인해야하고, 게시글에서 누가 글을 작성했는지 확인도 해야해서 양방향 객체관계가 이루어져야한다
     * mappedBy는 객체간 관계에서 주인이 아닌 곳에 작성
     * */
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    List<Board> boards;

    //댓글 (일대다)
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Comment> commentList;

    //추천 (일대다)
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Recommend> recommend;

    public void rankUp(UserRole userRole){
        this.userRole = userRole;
    }

}
