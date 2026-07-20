package az.librarycrudapi.Repository;

import az.librarycrudapi.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}