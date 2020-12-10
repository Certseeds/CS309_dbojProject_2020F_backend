package sustech.dbojbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.dbojbackend.model.SqlLanguage;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitUpdateQuestion {
    private Long programOrder;
    private SqlLanguage language;
    private List<String> group;
    private String correctCommand;
    private Long cputime;
    private Long momory;
}
