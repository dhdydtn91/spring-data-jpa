package study.datajpa.repository;

import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
          //클래스명은 MemberRepository + Impl로 규칙이 있음
          //Custom을 만들어서 모든 쿼리를 쓰는 건 좋지 않다.
          //쿼리용 + 핵심비지니스용으로 나눠서 사용하는게 좋음.
    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
