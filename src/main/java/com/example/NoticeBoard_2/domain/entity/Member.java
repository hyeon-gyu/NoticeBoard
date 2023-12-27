package com.example.NoticeBoard_2.domain.entity;


import com.example.NoticeBoard_2.common.TimeEntity;
import com.example.NoticeBoard_2.domain.enum_class.MemberRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member extends TimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(nullable = false)
    private String loginId; // 로그인할 때 사용할 아이디 정보

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Integer recommendCnt; // 총 추천받은 수

    @Enumerated(EnumType.STRING) // 유저 등급 지정, 테이블에 string으로 값 들어감
    private MemberRole memberRole;

    //== 생성자 Builder ==//
    @Builder
    public Member(String loginId, String password, String nickname, MemberRole memberRole, LocalDateTime signUpAt, Integer recommendCnt) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.memberRole = memberRole;
        this.recommendCnt = 0;
    }



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

    //orphanRemoval = true 특성 공부하기
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //회원정보 가져오기에서 작성한 댓글 전부 확인하기
    private List<Comment> comments = new ArrayList<>();
    //추천 (일대다)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //회원정보 가져오기에서 추천 누른 게시글 전부 확인하기
    private List<Recommend> recommend;

    public void rankUp(MemberRole memberRole){
        this.memberRole = memberRole;
    }


    //========== UserDetails implements ==========//
    /**
     * Token을 고유한 LoginId 값으로 생성합니다
     * @return LoginId;
     */

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add( new SimpleGrantedAuthority("ROLE_" + this.memberRole.name()));
        return authorities;
    }
}
