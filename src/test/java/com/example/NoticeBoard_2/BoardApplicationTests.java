package com.example.NoticeBoard_2;


import com.example.NoticeBoard_2.common.ResourceNotFoundException;
import com.example.NoticeBoard_2.domain.dto.request.board.BoardWriteDto;
import com.example.NoticeBoard_2.domain.dto.request.board.SearchData;
import com.example.NoticeBoard_2.domain.dto.request.comment.CommentRequestDto;
import com.example.NoticeBoard_2.domain.dto.response.board.BoardResponseDetailDto;
import com.example.NoticeBoard_2.domain.dto.response.board.BoardResponseListDto;
import com.example.NoticeBoard_2.domain.dto.response.comment.CommentResponseDto;
import com.example.NoticeBoard_2.domain.entity.Board;
import com.example.NoticeBoard_2.domain.entity.Comment;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.domain.entity.Recommend;
import com.example.NoticeBoard_2.domain.enum_class.BoardCategory;
import com.example.NoticeBoard_2.domain.enum_class.MemberRole;
import com.example.NoticeBoard_2.repository.BoardRepository;
import com.example.NoticeBoard_2.repository.CommentRepository;
import com.example.NoticeBoard_2.repository.MemberRepository;
import com.example.NoticeBoard_2.repository.RecommendRepository;
import com.example.NoticeBoard_2.service.BoardService;
import com.example.NoticeBoard_2.service.CommentService;
import com.example.NoticeBoard_2.service.MemberService;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@SpringBootTest

@Transactional
class BoardApplicationTests {

	@Autowired
	private EntityManager em;

	@Autowired
	private MemberService memberService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private CommentService commentService;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private RecommendRepository recommendRepository;

	private static final Logger logger = LogManager.getLogger(BoardApplication.class);

	@Test //회원탈퇴
	public void testMemberWithdraw(){
		Member member = new Member("1234","1234","gyugyu1", MemberRole.ASSOCIATE, LocalDateTime.now(),0);
		em.persist(member);

		//Assertions.assertEquals(byLoginId.get(), member); //성공

		Board board = new Board("hello","i am person", BoardCategory.GREETING, member, 0,0);
		Board board1 = new Board("hello232323","i am person1111", BoardCategory.FREE, member, 0,0);
		em.persist(board);
		em.persist(board1);
		em.flush();
		em.clear();
		Optional<Member> byLoginId = memberRepository.findByLoginId("1234");
		memberRepository.delete(byLoginId.get());
		em.flush();
		em.clear();

		List<Board> memberBoards = boardRepository.findAllByMember(member);
		Assertions.assertTrue(memberBoards.isEmpty());
	}

	@Test // 회원 카운트
	public void testTotalMemberCount(){
		Member member = new Member("1234","1234","gyugyu1", MemberRole.ASSOCIATE, LocalDateTime.now(),0);
		Member member1 = new Member("aaa","1234","aa", MemberRole.REGULAR, LocalDateTime.now(),0);
		Member member2 = new Member("bbb","1234","bb", MemberRole.ASSOCIATE, LocalDateTime.now(),0);
		Member member3 = new Member("ccc","1234","ccc", MemberRole.VIP, LocalDateTime.now(),0);
		Member member4 = new Member("ddd","1234","dd", MemberRole.ASSOCIATE, LocalDateTime.now(),0);
		Member member5 = new Member("eee","1234","eeeee", MemberRole.ASSOCIATE, LocalDateTime.now(),0);
		em.persist(member);em.persist(member1);    em.persist(member2);em.persist(member3);em.persist(member4);em.persist(member5);
		em.flush(); em.clear();
		List<Object[]> memberCntLists = memberRepository.countMemberByMemberRole();
		for (Object[] index : memberCntLists){
			logger.info(String.valueOf((MemberRole)index[0]));
			logger.info(String.valueOf((Long)index[1]));
		}
	}

	@Test //주제,본문,글쓴이 키워드로 검색
	public void testSearchByKeyword(){
		Member member = new Member("1234","1234","gyugyu", MemberRole.ASSOCIATE, LocalDateTime.now(),0);
		Board board = new Board("hello","i am person1", BoardCategory.GREETING, member, 0,0);
		Board board1 = new Board("hel133","i am person2", BoardCategory.GREETING, member, 0,0);
		Board board2 = new Board("helo2","i am person3", BoardCategory.GREETING, member, 0,0);
		Board board3 = new Board("hello1","i am person4", BoardCategory.GREETING, member, 0,0);
		Member member1 = new Member("12345","12345","gyugyu5", MemberRole.ASSOCIATE, LocalDateTime.now(),0);
		Board board4 = new Board("human","i am not person123333322", BoardCategory.GREETING, member1, 0,0);
		em.persist(member); em.persist(board);em.persist(board1);em.persist(board2); em.persist(member1); em.persist(board4); em.persist(board3);
		em.flush(); em.clear();

		SearchData searchData1 = SearchData.createSearchData(null, null, "not");
		List<BoardResponseListDto> search1 = boardService.search(searchData1);
		logger.info(search1.size());
		for(BoardResponseListDto index: search1){
			logger.info(index.getWriterName());
		}
	}


	@Test //게시글 상세보기
	public void testDetail(){
		BoardResponseDetailDto detail = boardService.detail(2L);
		logger.info(detail.getTitle() + detail.getContent() + detail.getCreatedDate() + detail.getCommentCnt());
	}

	@Test
	@Transactional
	@DisplayName("게시글 수정 테스트")
	public void editTest(){
		Member member = new Member("1234","1234","gyugyu", MemberRole.ASSOCIATE, LocalDateTime.now(),0);
		em.persist(member); em.flush();
		Member findMember = memberRepository.findByLoginId("1234").orElseThrow(
				() -> new ResourceNotFoundException("Member", "Member loginId", String.valueOf(1234))
		);
		Board board = new Board("hello","i am person1", BoardCategory.GREETING, findMember, 0,0);
		em.persist(board); em.flush();

		Board findBoard = boardRepository.findByBoardId(5L).orElseThrow(
				() -> new ResourceNotFoundException("Board", "Board Id", String.valueOf(5L))
		);
		BoardWriteDto boardWriteDto = new BoardWriteDto("수정제목","수정내용");
		BoardResponseDetailDto edit = boardService.edit(findMember, 5L, boardWriteDto);
		logger.info(edit.getTitle() + edit.getContent());
	}

	@Test
	@DisplayName("게시글 추천하기 테스트")
	public void recommendTest(){ //게시글 추천하기 테스트
		Member member = memberRepository.findByLoginId("1223").orElseThrow(
				() -> new ResourceNotFoundException("Member", "Member loginId", String.valueOf(1223))
		);
		Board board = boardRepository.findByBoardId(5L).orElseThrow(
				() -> new ResourceNotFoundException("Board", "Board Id", String.valueOf(5L))
		);


		boardService.recommend(member,board.getId());
		em.flush(); em.clear();

		Board board1 = boardRepository.findByBoardId(5L).orElseThrow(
				() -> new ResourceNotFoundException("Board", "Board Id", String.valueOf(5L))
		);
		logger.info(board1.getRecommendCnt());
	}

	@Test
	public void recommendUndoTest(){
		Member member = memberRepository.findByLoginId("1228").orElseThrow(
				() -> new ResourceNotFoundException("Member", "Member loginId", String.valueOf(1228))
		);
		Board board = boardRepository.findByBoardId(3L).orElseThrow(
				() -> new ResourceNotFoundException("Board", "Board Id", String.valueOf(3L))
		);
		logger.info("현재 추천 수 :"+board.getRecommendCnt());
		boardService.recommend(member,3L);
		em.flush(); em.clear();
		Board board1 = boardRepository.findByBoardId(5L).orElseThrow(
				() -> new ResourceNotFoundException("Board", "Board Id", String.valueOf(5L))
		);
		logger.info("현재 추천 수 :"+board1.getRecommendCnt());
		//현재 db에 1228 아이디 회원이 3번 보드에 추천 누른 상황
		boardService.recommend_undo(member,5L);

		em.flush(); em.clear();
		logger.info("현재 추천 수 :"+board1.getRecommendCnt());
	}

	@Test

	public void sortTest(){
		List<BoardResponseListDto> res = boardService.sorting("commentCnt");
		for(BoardResponseListDto index: res){
			logger.info(index.getContent());
		}
	}

	@Test
	@DisplayName("페이징처리")
	public void pageTest(){
		Pageable pageable = PageRequest.of(0,2);
		Page<BoardResponseListDto> allBoards = boardService.getAllBoards(pageable);
		logger.info(allBoards.getContent());
	}
}
