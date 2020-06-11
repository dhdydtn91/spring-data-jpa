package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

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

}

