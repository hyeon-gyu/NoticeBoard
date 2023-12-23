package com.example.NoticeBoard_2.service;


import com.example.NoticeBoard_2.common.ResourceNotFoundException;
import com.example.NoticeBoard_2.domain.dto.request.BoardWriteDto;
import com.example.NoticeBoard_2.domain.dto.response.BoardResponseWriteDto;
import com.example.NoticeBoard_2.domain.entity.Board;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.domain.enum_class.MemberRole;
import com.example.NoticeBoard_2.repository.BoardRepository;
import com.example.NoticeBoard_2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public BoardResponseWriteDto write(String category, BoardWriteDto boardWriteDto, Member member){
        //DTO를 ENTITY로 바꾸기 -> member 객체로 회원 찾기 -> 로그인아이디로 멤버 판별 -> 글 저장
        Board board = BoardWriteDto.applyCategory(boardWriteDto, category); //카테고리 적용까지 완료
        Member writeMember = memberRepository.findByLoginId(member.getLoginId()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member LoginId", member.getLoginId()));

        if (category.equalsIgnoreCase("greeting")){member.rankUp(MemberRole.REGULAR);} //준회원 + 가입인사 게시판 글쓰기 => 등업
        board.setMappingMember(writeMember); // 양방향 연관관계 적용
        Board saveBoard = boardRepository.save(board);
        return BoardResponseWriteDto.fromEntity(saveBoard, writeMember.getNickname());
    }
}
