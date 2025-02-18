package _2._millionaire.groupjoin;

import _2._millionaire.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupJoinRepository extends JpaRepository<GroupJoin, Long> {

    List<Member> findByGroups(Long groupId);
}
