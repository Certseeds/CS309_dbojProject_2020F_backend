package sustech.dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import sustech.dbojbackend.model.UserLevel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
    @GeneratedValue
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
}