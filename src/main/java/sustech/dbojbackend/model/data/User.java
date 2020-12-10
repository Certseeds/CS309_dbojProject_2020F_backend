package sustech.dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import sustech.dbojbackend.model.UserLevel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @Column(name = "emall", nullable = false, unique = true)
    private String email;
    @Column(name = "user_level", nullable = false)
    private UserLevel level;

    public User() {
    }

    public User(String userName, String passWord, String Email, UserLevel level) {
        this.userName = userName;
        this.passWord = passWord;
        this.level = level;
        this.email = Email;
    }

}