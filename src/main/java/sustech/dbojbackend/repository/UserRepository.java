package sustech.dbojbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import sustech.dbojbackend.model.data.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional(timeout = 10)
    List<User> findByUserNameAndPassWord(String userName, String passWord);

}

