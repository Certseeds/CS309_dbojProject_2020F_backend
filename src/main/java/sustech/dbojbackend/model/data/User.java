package sustech.dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import sustech.dbojbackend.model.UserLevel;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@Table(name = "user_table", schema = "public")
public class User implements Serializable {
    private static final long serialVersionUID = 125183331L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String userName;
    @Column(name = "password", nullable = false)
    private String passWord;
    @Column(name = "emall", nullable = true, unique = true)
    private String email;
    @Column(name = "user_level", nullable = false)
    private UserLevel level;

    public User() {
    }

    public User(String userName, String passWord, UserLevel level) {
        this.userName = userName;
        this.passWord = passWord;
        this.level = level;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserLevel getLevel() {
        return level;
    }

    public void setLevel(UserLevel level) {
        this.level = level;
    }
}