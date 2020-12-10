package sustech.dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.dbojbackend.model.SqlLanguage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "QUESTION_DETAIL", schema = "public")
@IdClass(QuestionDetailUpk.class)
public class QuestionDetail {
    @Id
    @Column(name = "program_order")
    private Long programOrder;
    @Column(name = "correct_script", nullable = false)
    private String correctScript;
    @Id
    @Column(name = "language", nullable = false)
    private SqlLanguage language;
    @Column(name = "cputime", nullable = false)
    private Long cputime;
    @Column(name = "memory", nullable = false)
    private Long memory;

}
