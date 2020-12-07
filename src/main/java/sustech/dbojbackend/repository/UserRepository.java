package sustech.dbojbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sustech.dbojbackend.model.UserLevel;
import sustech.dbojbackend.model.data.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional(timeout = 10)
    List<User> findByUserNameAndPassWord(String userName, String passWord);

    @Transactional(timeout = 10)
    List<User> findByUserName(String userName);

    @Transactional(timeout = 10)
    List<User> findByEmail(String email);

    @Transactional(timeout = 10)
    @Query(value = "UPDATE User u SET u.passWord = :newPassword WHERE u.id = :id")
    @Modifying
    Integer updatePasswordById(@Param(value = "id") Long id, @Param(value = "newPassword") String newPassword);

    @Transactional(timeout = 10)
    @Query(value = "UPDATE User u SET u.level = :level WHERE u.id = :id")
    @Modifying
    Integer updateLevelById(@Param(value = "id") Long id, @Param(value = "level") UserLevel level);

    @Transactional(timeout = 10)
    @Query(value = "UPDATE User u SET u.passWord = :newPassword WHERE u.userName = :userName")
    @Modifying
    Integer updatePasswordByUsername(@Param(value = "userName") String userName, @Param(value = "newPassword") String newPassword);


    @Transactional(timeout = 10)
    List<User> findByid(Long id);


}

