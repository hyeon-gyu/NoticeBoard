package com.example.NoticeBoard_2.repository;


import com.example.NoticeBoard_2.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId); //아이디로 검색
    Optional<Member> findByNickname(String nickname); //닉네임으로 검색
//    Boolean existsByLoginId(String loginId); // 아이디 중복 검사 (회원가입시 중복되는 아이디 금지)
//    Boolean existsByNickname(String nickname); // 닉네임 중복검사
}
