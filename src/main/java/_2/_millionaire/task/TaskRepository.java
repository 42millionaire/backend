package _2._millionaire.task;

import _2._millionaire.groupmember.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByGroupMemberAndDueDate(GroupMember groupMember, LocalDate dueDate);

    List<Task> findByDueDateAndStatus(LocalDate dueDate, String status);
}
