package com.example.NoticeBoard_2.repository;

import com.example.NoticeBoard_2.domain.entity.Board;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.domain.enum_class.BoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByBoardCategory(BoardCategory category);
    List<Board> findAllByMember(Member member);
}
