package com.example.NoticeBoard_2.controller;

import com.example.NoticeBoard_2.domain.dto.request.BoardWriteDto;
import com.example.NoticeBoard_2.domain.dto.response.BoardResponseListDto;
import com.example.NoticeBoard_2.domain.dto.response.BoardResponseWriteDto;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.domain.enum_class.BoardCategory;
import com.example.NoticeBoard_2.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    /** 게시판 글쓰기 */
    @PostMapping("/write/{category}")
    public ResponseEntity<BoardResponseWriteDto> write(
            @PathVariable("category") String category,
            @RequestBody BoardWriteDto boardWriteDto,
            @AuthenticationPrincipal Member member){
        Thread currentThread = Thread.currentThread();
//        log.info("현재 실행 중인 스레드: " + currentThread.getName());
        BoardResponseWriteDto saveBoard = boardService.write(category, boardWriteDto, member); //저장정보 리턴
        return ResponseEntity.status(HttpStatus.OK).body(saveBoard);
    }

    /** 게시판 카테고리별로 불러오기 */
    @GetMapping("/list/{category}")
    public ResponseEntity<List<BoardResponseListDto>> boardList(@PathVariable("category") String category){
        BoardCategory boardCategory = BoardCategory.of(category);
        List<BoardResponseListDto> responseBoardList = boardService.boardList(boardCategory);
        return ResponseEntity.status(HttpStatus.OK).body(responseBoardList);
    }

}
