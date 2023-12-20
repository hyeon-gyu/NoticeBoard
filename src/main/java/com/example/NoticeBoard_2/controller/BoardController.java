package com.example.NoticeBoard_2.controller;

import com.example.NoticeBoard_2.common.ApiResponse;
import com.example.NoticeBoard_2.domain.dto.WriteBoardRequest;
import com.example.NoticeBoard_2.domain.enum_class.BoardCategory;
import com.example.NoticeBoard_2.service.BoardService;
import com.example.NoticeBoard_2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    /** 게시판 불러오기 (카테고리별) */
    @GetMapping("/{category}")
    public ApiResponse getBoardByCategory(@PathVariable String boardCategory){
        return boardService.getBoardByCategory(boardCategory);
    }

//    /** 게시판 작성하고 완료 누를 때 (카테고리구분)*/
//    @PostMapping("/{category}/write")
//    public ApiResponse writeBoardByCategory(@RequestBody WriteBoardRequest writeBoardRequest){
//
//    }

//
//    /** 게시판 글쓰기 버튼 누를 때 */
//    @GetMapping("/{category}/write")
//    public
}
