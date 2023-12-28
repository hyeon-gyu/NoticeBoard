package com.example.NoticeBoard_2.controller;


import com.example.NoticeBoard_2.domain.dto.request.comment.CommentRequestDto;
import com.example.NoticeBoard_2.domain.dto.response.comment.CommentResponseDto;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/{boardId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /** 댓글 작성하기 */
    @PostMapping("/write")
    public ResponseEntity<CommentResponseDto> write(
            @AuthenticationPrincipal Member member,
            @RequestBody CommentRequestDto commentDto,
            @PathVariable("boardId") Long boardId
            ){

        CommentResponseDto commentResponseDto = commentService.write(member, commentDto, boardId);
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }
}
