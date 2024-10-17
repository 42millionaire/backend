package _2._millionaire.groupmember;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    Optional<GroupMember> findByTasks_Id(Long taskId);

    Optional<GroupMember> findByMember_Id(Long memberId);

    Optional<List<GroupMember>> findAllByMember_Id(Long memberId);
}
