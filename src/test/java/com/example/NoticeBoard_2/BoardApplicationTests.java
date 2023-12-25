package com.example.NoticeBoard_2;

import com.example.NoticeBoard_2.domain.dto.request.MemberLoginDto;
import com.example.NoticeBoard_2.domain.dto.request.MemberSignupDto;
import com.example.NoticeBoard_2.domain.dto.response.MemberResponseDto;
import com.example.NoticeBoard_2.domain.entity.Board;
import com.example.NoticeBoard_2.domain.entity.Member;
import com.example.NoticeBoard_2.domain.enum_class.BoardCategory;
import com.example.NoticeBoard_2.domain.enum_class.MemberRole;
import com.example.NoticeBoard_2.repository.BoardRepository;
import com.example.NoticeBoard_2.repository.MemberRepository;
import com.example.NoticeBoard_2.service.MemberService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest

@Transactional
class BoardApplicationTests {

	@Autowired
	private EntityManager em;

	@Autowired
	private MemberService memberService;

	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private MemberRepository memberRepository;


	@Test
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

	@Test
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
//			logger.info(String.valueOf((MemberRole)index[0]));
//			logger.info(String.valueOf((Long)index[1]));
		}
	}

}
