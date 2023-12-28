package com.example.NoticeBoard_2.service;


import com.example.NoticeBoard_2.common.ResourceNotFoundException;
import com.example.NoticeBoard_2.domain.dto.request.board.BoardWriteDto;
import com.example.NoticeBoard_2.domain.dto.request.board.SearchData;
import com.example.NoticeBoard_2.domain.dto.response.board.BoardResponseDetailDto;
import com.example.NoticeBoard_2.domain.dto.response.board.BoardResponseListDto;
import com.example.NoticeBoard_2.domain.dto.response.board.BoardResponseWriteDto;
import com.example.NoticeBoard_2.domain.entity.Board;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.domain.enum_class.BoardCategory;
import com.example.NoticeBoard_2.domain.enum_class.MemberRole;
import com.example.NoticeBoard_2.repository.BoardRepository;
import com.example.NoticeBoard_2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    // 게시글 작성하는 서비스 로직
    public BoardResponseWriteDto write(String category, BoardWriteDto boardWriteDto, Member member){
        //DTO를 ENTITY로 바꾸기 -> member 객체로 회원 찾기 -> 로그인아이디로 멤버 판별 -> 글 저장
        Board board = BoardWriteDto.applyCategory(boardWriteDto, category); //카테고리 적용까지 완료
        Member writeMember = memberRepository.findByLoginId(member.getLoginId()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member LoginId", member.getLoginId()));

        if (category.equalsIgnoreCase("greeting") && member.getMemberRole() == MemberRole.ASSOCIATE){
            member.rankUp(MemberRole.REGULAR);} //준회원 + 가입인사 게시판 글쓰기 => 등업
        board.setMappingMember(writeMember); // 양방향 연관관계 적용
        Board saveBoard = boardRepository.save(board);
        return BoardResponseWriteDto.fromEntity(saveBoard, writeMember.getNickname());
    }

    // 카테고리로 게시물 조회하는 서비스 로직
    public List<BoardResponseListDto> boardList(BoardCategory category){
        List<Board> boards = boardRepository.findByBoardCategory(category);
        return boards.stream().map(BoardResponseListDto::fromEntity).toList();
    }

    //검색 서비스 로직
    public List<BoardResponseListDto> search(SearchData searchData){
        List<Board> boardList = new ArrayList<>();
        if(searchData.getTitle() != null){
            boardList = boardRepository.findAllTitleContaining(searchData.getTitle());
        }
        else if(searchData.getWriter() != null){
            boardList = boardRepository.findAllWriterContaining(searchData.getWriter());
        }
        else if(searchData.getContent() != null){
            boardList = boardRepository.findAllBodyContaining(searchData.getContent());
        }
        return boardList.stream().map(BoardResponseListDto::fromEntity).collect(Collectors.toList());
    }

    // 게시글 상세보기 ( 게시글 하나 클릭했을 때)
    public BoardResponseDetailDto detail(Long boardId){
        Board findBoard = boardRepository.findByBoardId(boardId).orElseThrow(
                () -> new ResourceNotFoundException("Board", "Board Id", String.valueOf(boardId)));
        return BoardResponseDetailDto.fromEntity(findBoard);
    }

    // 게시글 수정하기
    public BoardResponseDetailDto edit(Member member, Long boardId, BoardWriteDto dto) {

        Board board = boardRepository.findByBoardId(boardId).orElseThrow(
                () -> new ResourceNotFoundException("Board", "Board Id", String.valueOf(boardId)));
        if(Objects.equals(board.getMember().getLoginId(), member.getLoginId())){ // 게시글 작성자와 member가 동일인이여야 수정 진행
            board.update(dto.getTitle(), dto.getContent());
            return BoardResponseDetailDto.fromEntity(board);
        }
        else {
            return BoardResponseDetailDto.fromEntity(board); //어찌 처리할꼬? 글쓴이와 동일하지 않으면 수정이 안되는걸루 그냥 가자!
        }
    }
}
