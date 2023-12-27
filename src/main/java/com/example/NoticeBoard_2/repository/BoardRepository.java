package com.example.NoticeBoard_2.repository;

import com.example.NoticeBoard_2.domain.entity.Board;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.domain.enum_class.BoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByBoardCategory(BoardCategory category);
    List<Board> findAllByMember(Member member);

    // boardId로 검색 : 게시글 상세보기
    @Query(value = "SELECT b FROM Board b JOIN FETCH b.member WHERE b.id = :boardId")
    Optional<Board> findByBoardId(@Param("boardId") Long boardId);

    @Query("SELECT b FROM Board b JOIN FETCH b.member WHERE b.title LIKE %:title%")
    List<Board> findAllTitleContaining(@Param("title")String title);

    @Query("SELECT b FROM Board b JOIN FETCH b.member WHERE b.member.nickname LIKE %:writer%")
    List<Board> findAllWriterContaining(@Param("writer")String writer);

    @Query("SELECT b FROM Board b JOIN FETCH b.member WHERE b.content LIKE %:content%")
    List<Board> findAllBodyContaining(@Param("content") String content);
}
