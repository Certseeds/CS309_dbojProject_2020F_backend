package sustech.dbojbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import sustech.dbojbackend.model.SqlLanguage;


import java.util.Date;
@Data
@AllArgsConstructor
public class QuestionHead {
    private Long programOrder;
    private String name;
    private String description;
    private SqlLanguage language;
    private Date deadline;
}
