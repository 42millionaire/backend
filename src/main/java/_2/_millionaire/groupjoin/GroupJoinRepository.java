package _2._millionaire.groupjoin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupJoinRepository extends JpaRepository<GroupJoin, Long> {
}
