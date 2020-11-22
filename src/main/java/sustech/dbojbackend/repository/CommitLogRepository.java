package sustech.dbojbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import sustech.dbojbackend.model.data.CommitLog;

import java.util.List;

public interface CommitLogRepository extends JpaRepository<CommitLog,Long> {
    @Transactional(timeout = 10)
    List<CommitLog> findByCommitLogId(Long id);


}
