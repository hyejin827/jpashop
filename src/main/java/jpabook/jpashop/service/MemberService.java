package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import jpabook.jpashop.dto.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

//    @Autowired 생략가능
//    AllArgsConstructor 있으면 생성자 자동으로 만들어줌
//    RequiredArgsConstructor 있으면 final만 생성자 자동으로 만들어줌
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

     /*
     * 회원가입
     * 조회 아닌경우에는 readOnly true X (디폴트 false)
     */
     @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원");
        }
    }

    /*
    * 회원전체조회
    */
    public List<MemberResponseDto> findMembers() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberResponseDto> memberResList = new ArrayList<MemberResponseDto>();

        memberList.forEach(member -> {
            MemberResponseDto mbr = new MemberResponseDto(member);
            memberResList.add(mbr);
        });

        return memberResList;
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
