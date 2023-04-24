package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/list")
    public List<Member> list() {
        List<Member> memberList = memberService.findMembers();
        return memberList;
    }

    @PostMapping("/new")
    public String create(@RequestBody Member member) {
        memberService.join(member);
        log.info("member => ", member);
        return "success";
    }
}
