package sustech.dbojbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import sustech.dbojbackend.model.data.QuestionBuild;

public interface QuestionBuildRepository extends JpaRepository<QuestionBuild, Long> {
    @Transactional(timeout = 10)
    @Modifying
    Integer deleteByProgramOrder(Long order);
}
