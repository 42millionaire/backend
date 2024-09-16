package _2._millionaire.verification;

import _2._millionaire.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    Optional<Verification> findByTask(Task task);
    Optional<Verification> findByTaskId(Long taskId);
}
