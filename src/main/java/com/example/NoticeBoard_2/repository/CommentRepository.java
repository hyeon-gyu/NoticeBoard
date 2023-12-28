package com.example.NoticeBoard_2.repository;

import com.example.NoticeBoard_2.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
