package jpabook.jpashop.domain.Service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@Transactional(readOnly = true) // 읽기에는 가급적 이걸 넣어줌 (데이텨 변경 필오 없음)
@RequiredArgsConstructor //final이 있는 필드만 가지고 생성자 만들어줌
public class MemberService {


    private final MemberRepository memberRepository;


    /**
     * 회원가입
     */
    @Transactional //기본은 false임
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
        //em.persist할때 이미 키값이 고정이됨 -> member엔티티에서 generatedvalue 해줬기때문
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");//최적화 방법 = 사이즈를 재서 findmembers 가 0이상이면 익셉션터트림
        }
    }


    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
