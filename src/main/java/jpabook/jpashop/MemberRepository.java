package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;

@org.springframework.stereotype.Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;


    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

}
