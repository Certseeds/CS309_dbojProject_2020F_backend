package sustech.dbojbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import sustech.dbojbackend.model.SqlLanguage;
import sustech.dbojbackend.model.data.QuestionDetail;

import java.util.List;

public interface QuestionDetailRepository extends JpaRepository<QuestionDetail, Long> {
    @Transactional(timeout = 10)
    List<QuestionDetail> findByProgramOrderAndLanguage(Long programOrder, SqlLanguage language);

}
