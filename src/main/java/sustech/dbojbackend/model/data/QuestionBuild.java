package sustech.dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import sustech.dbojbackend.model.SqlLanguage;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "question_build", schema = "public")
public class QuestionBuild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_table_order")
    private Long questionTableOrder;
    @Column(name = "program_order")
    private Long programOrder;
    @Column(name = "build_script", nullable = false)
    private String buildScript;
    @Column(name = "language", nullable = false)
    private SqlLanguage language;

    public QuestionBuild() {

    }
}
