package com.example.NoticeBoard_2.repository;


import com.example.NoticeBoard_2.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId); //아이디로 검색
    Optional<Member> findByNickname(String nickname); //닉네임으로 검색

    @Query("SELECT m.memberRole, COUNT(m) FROM Member m WHERE m.memberRole != 'ADMIN' GROUP BY m.memberRole")
    List<Object[]> countMemberByMemberRole(); // 조회대상이 둘 이상이면 object list를 활용하자
}
