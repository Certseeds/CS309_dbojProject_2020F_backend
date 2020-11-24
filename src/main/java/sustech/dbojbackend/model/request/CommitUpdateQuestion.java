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
    public Long programOrder;
    public SqlLanguage language;
    public List<String> group;
    public String correctCommand;
}
