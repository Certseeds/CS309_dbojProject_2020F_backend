package sustech.dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.dbojbackend.model.SqlLanguage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question_build", schema = "public")
public class QuestionBuild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_table_order",nullable = false)
    private Long questionTableOrder;
    @Column(name = "program_order",nullable = false)
    private Long programOrder;
    @Column(name = "build_script", nullable = false)
    private String buildScript;
    @Column(name = "language", nullable = false)
    private SqlLanguage language;

}
