package com.example.NoticeBoard_2.repository;


import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.domain.enum_class.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId); //아이디로 검색
    Optional<Member> findByNickname(String nickname); //닉네임으로 검색

    // SELECT M.MEMBERROLE, COUNT(M.MEMBER_ID) FROM MEMBER M WHERE M.MEMBERROLE != ADMIN GROUP BY M.MEMBERROLE
    @Query("SELECT m.memberRole, COUNT(m) FROM Member m WHERE m.memberRole != 'ADMIN' GROUP BY m.memberRole")
    List<Object[]> countMemberByMemberRole();
}
