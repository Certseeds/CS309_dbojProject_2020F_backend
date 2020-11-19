package sustech.dbojbackend.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sustech.dbojbackend.model.data.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional(timeout = 10)
    List<User> findByUserNameAndPassWord(String userName, String passWord);

    @Transactional(timeout = 10)
    @Query(value = "UPDATE User u SET u.passWord = :newPassword WHERE u.id = :id")
    @Modifying
    Integer updatePasswordById(@Param(value = "id") Long id, @Param(value = "newPassword") String newPassword);

}

