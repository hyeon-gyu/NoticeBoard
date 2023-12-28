package com.example.NoticeBoard_2.repository;

import com.example.NoticeBoard_2.domain.entity.Board;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.domain.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {

    Optional<Recommend> findByMemberAndBoard(Member member, Board board);
}
