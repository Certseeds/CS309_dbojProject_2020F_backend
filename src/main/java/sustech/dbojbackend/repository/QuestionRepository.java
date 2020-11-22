package sustech.dbojbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sustech.dbojbackend.model.data.Question;
import sustech.dbojbackend.model.data.User;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Transactional(timeout = 10)
    List<Question> findByProgramOrder(Long id);
}
