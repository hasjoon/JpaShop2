package jpabook.jpashop.domain.Service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * 회원가입
     */
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
        //em.persist할때 이미 키값이 고정이됨
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");//5:24 최적화방법
        }

    }


    //회원 전체 조회
    @GetMapping("/v1/member")
    public void findAllMember(){
        List<Member> allMember = memberRepository.findAll();
    }

}
