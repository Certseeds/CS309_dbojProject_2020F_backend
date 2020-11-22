package sustech.dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;
import sustech.dbojbackend.model.SqlLanguage;
import sustech.dbojbackend.model.UserLevel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@Table(name = "question", schema = "public")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_order")
    private Long programOrder;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "language", nullable = false)
    private SqlLanguage language;
    @Column(name = "deadline", nullable = false)
    private Date deadline;

    public Question() {
    }
}
