package sustech.dbojbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import sustech.dbojbackend.model.data.CommitResult;

import java.util.List;

public interface CommitResultRepository extends JpaRepository<CommitResult, Long> {
    @Transactional(timeout = 10)
    List<CommitResult> findByCommitLogId(Long id);
}
