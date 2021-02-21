package jpabook.jpashop.controller;

import jpabook.jpashop.Service.MemberService;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result) {
        //정말 간단한 로직이 아니라면 Entitiy를 그대로 사용하지 않는 것이 좋다
        //Entity는 비즈니스 로직에만 의존하도록 깔끔하게 만들어야 한다

        //BindingResult result가 없으면 validate 실패시 함수가 수행되지 않는다.
        //BindingResult result가 있으면 result에 에러 결과를 담고 함수를 수행한다.
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/"; // 맨 처음 페이지로 간다
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        //Dto를 사용하는 것을 추천한다
        //API를 만들때는 절대로 Entity를 반환해서는 안된다
        return "/members/memberList";
    }
}
