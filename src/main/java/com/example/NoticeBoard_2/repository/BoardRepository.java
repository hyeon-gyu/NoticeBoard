package com.example.NoticeBoard_2.repository;

import com.example.NoticeBoard_2.domain.entity.Board;
import com.example.NoticeBoard_2.domain.enum_class.BoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByBoardCategory(String category);
}
