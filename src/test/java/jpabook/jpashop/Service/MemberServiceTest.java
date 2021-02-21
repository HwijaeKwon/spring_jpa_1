package jpabook.jpashop.Service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class) //Spring이랑 같이 테스트하겠다
@SpringBootTest //Spring boot를 사용해서 테스트하겠다
@Transactional //Test 끝나면 rollback 하겠다
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    //@Rollback(false) //commit 하고 싶을 때
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kwon");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush(); // roll back 전에 insert 되는 것 보고 싶을 때
        Assert.assertEquals(member, memberRepository.findById(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kwon");

        Member member2 = new Member();
        member2.setName("kwon");

        //when
        memberService.join(member1);
        //then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

}