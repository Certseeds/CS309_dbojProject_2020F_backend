package sustech.dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import sustech.dbojbackend.model.UserLevel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@Table(name = "QUESTION_DETAIL", schema = "public")
public class QuestionDetail {
    @Id
    @Column(name = "program_order")
    private Long programOrder;
    @Column(name = "correct_script", nullable = false)
    private String correctScript;

    public QuestionDetail() {

    }
}
