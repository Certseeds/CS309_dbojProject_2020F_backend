package sustech.dbojbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.dbojbackend.model.data.Question;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionWithAcNumberAndSubmission {
    private Long programOrder;
    private String name;
    private String description;
    private Long deadline; // this is the second form standard time
    private Long acNumber;
    private Long submission;

    public QuestionWithAcNumberAndSubmission(Question que, Long ac, Long submission) {
        this.programOrder = que.getProgramOrder();
        this.name = que.getName();
        this.description = que.getDescription();
        this.deadline = que.getDeadline();
        this.acNumber = ac;
        this.submission = submission;
    }
}