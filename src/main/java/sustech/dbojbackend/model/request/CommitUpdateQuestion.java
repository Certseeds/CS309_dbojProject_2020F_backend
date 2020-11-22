package sustech.dbojbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import sustech.dbojbackend.model.SqlLanguage;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class CommitUpdateQuestion {
    public String name;
    public String description;
    public SqlLanguage language;
    public List<String> group;
    public Date DDL;
    public String correctCommand;
}
