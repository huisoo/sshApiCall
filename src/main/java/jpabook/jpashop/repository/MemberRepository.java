package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
//@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext // 영속성 컨택스트
    //@Autowired //사용가능
    private final EntityManager em; // spring-boot-data-jpa 가 알아서 엔티티매니저를 생성해줌
    //JPA는 EntityManager와 영속성 컨텍스트를 통해 데이터의 상태 변화를 감지하고 필요한 쿼리를 자동으로 수행한다.

    @Autowired
    public MemberRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Member member){
        em.persist(member); //command 분리
        //return member.getId(); // ID를 리턴하면 다시 조회 할 경우 사용이 가능하여, 넣는다.
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        List<Member> memberList = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return memberList;
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name= :name", Member.class)
                .setParameter("name", name).getResultList();

    }

}
