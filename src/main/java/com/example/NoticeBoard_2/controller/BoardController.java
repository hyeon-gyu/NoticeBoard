package com.example.NoticeBoard_2.controller;

import com.example.NoticeBoard_2.common.AlreadyExistsException;
import com.example.NoticeBoard_2.common.ResourceNotFoundException;
import com.example.NoticeBoard_2.domain.dto.request.board.BoardWriteDto;
import com.example.NoticeBoard_2.domain.dto.request.board.SearchData;
import com.example.NoticeBoard_2.domain.dto.response.board.BoardResponseDetailDto;
import com.example.NoticeBoard_2.domain.dto.response.board.BoardResponseListDto;
import com.example.NoticeBoard_2.domain.dto.response.board.BoardResponseWriteDto;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.domain.enum_class.BoardCategory;
import com.example.NoticeBoard_2.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scripting.support.RefreshableScriptTargetSource;
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

    /** 제목, 글쓴이, 본문 키워드로 검색*/
    @GetMapping("/search")
    public ResponseEntity<List<BoardResponseListDto>> search(
            @RequestParam(required = false, name = "title") String title, // 해당 파라미터가 필수가 아니어도 괜찮음
            @RequestParam(required = false, name = "writer") String writer,
            @RequestParam(required = false, name = "content") String body) {
        SearchData searchData = SearchData.createSearchData(title, writer, body);
        List<BoardResponseListDto> searchDto = boardService.search(searchData);
        return ResponseEntity.status(HttpStatus.OK).body(searchDto);
    }

    /** 게시글 하나를 클릭할 때 (리턴 값으로 게시글 상세 정보 줘야함)*/
    @GetMapping("/detail/{boardId}")
    public ResponseEntity<BoardResponseDetailDto> detail(
            @PathVariable("boardId") Long boardId){
        BoardResponseDetailDto findBoardDto = boardService.detail(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(findBoardDto);
    }

    /** 게시글 수정하기 */
    @PostMapping("/edit/{boardId}")
    public ResponseEntity<BoardResponseDetailDto> edit(
            @AuthenticationPrincipal Member member,
            @PathVariable("boardId")Long boardId,
            @RequestBody BoardWriteDto dto){
        BoardResponseDetailDto editBoard = boardService.edit(member, boardId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(editBoard);
    }

    @GetMapping("/recommend/{boardId}")
    // 게시물에는 한 번만 추천을 누를 수 있음. // undo 작업까지 진행할 예정
    public ResponseEntity<?> recommend(
            @AuthenticationPrincipal Member member,
            @PathVariable("boardId")Long boardId){
        try {
            BoardResponseDetailDto recommend = boardService.recommend(member, boardId);
            return ResponseEntity.status(HttpStatus.OK).body(recommend);
        } catch (AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
