package com.example.NoticeBoard_2.service;


import com.example.NoticeBoard_2.common.ResourceNotFoundException;
import com.example.NoticeBoard_2.domain.dto.request.comment.CommentRequestDto;
import com.example.NoticeBoard_2.domain.dto.response.comment.CommentResponseDto;
import com.example.NoticeBoard_2.domain.entity.Board;
import com.example.NoticeBoard_2.domain.entity.Comment;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.repository.BoardRepository;
import com.example.NoticeBoard_2.repository.CommentRepository;
import com.example.NoticeBoard_2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class CommentService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto write(Member member, CommentRequestDto dto, Long boardId){
        // boardId로 게시글 주인 찾고 -> member.getId로 댓글작성자 찾고 -> comment 엔티티 객체에 정보 기입 -> save
        Member commentWriter = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member id", String.valueOf(member.getId())));
        Board findBoard = boardRepository.findByBoardId(boardId).orElseThrow(
                () -> new ResourceNotFoundException("Board", "Board id", String.valueOf(boardId)));
        Comment comment = CommentRequestDto.ofEntity(dto);
        comment.setBoard(findBoard);
        comment.setMember(commentWriter);
        findBoard.addComment(); //댓글 수 1 추가
        commentRepository.save(comment);
        return CommentResponseDto.fromEntity(comment);
    }
}
