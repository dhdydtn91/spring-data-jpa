package study.datajpa.repository;

import org.hibernate.annotations.common.reflection.XMember;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.Entity;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username,t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username); //컬렉션  결과가 없으면 Null이 아닌 Empty 컬렉션 반환
    Member findMemberByUsername(String username);   //단건 결과가 없으면 Null을 반환
    Optional<Member> findOptionalByUsername(String username); //단건 Optional 있을지 없을지 모르기 때문에 Optional로 사용하기 단건조회가아닌 여러개 조회값이 조회됬을땐 예외가 발생

    @Query(value = "select m from Member m left join m.team",
            countQuery = "select count(m) from Member m")//totalcount용 쿼리르 따로 지정할 수 있음
    Page<Member> findByAge(int age, org.springframework.data.domain.Pageable pageable);

    @Modifying(clearAutomatically = true) //executeUpdate와 같은 역활
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

//    @Query("select m from Member m left join fetch m.team")
//    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"}) //패치조인과 같이 한방 쿼리 나감
    List<Member> findAll();

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value ="true")) //변경감지 기능을 끔으로써 read 기능을 최적화 한다.
    Member findReadOnlyByUsername(String username);  //맨처음 부터 최적화를 하기는게 아니고 성능테스트를 해보고 써야함. -> 그리고 이걸 쓸 단계라면 redis를 사용하여 캐싱을 하는 것을 고려해볼 단계

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}

