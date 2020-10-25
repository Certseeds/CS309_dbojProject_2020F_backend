package sustech.dbojbackend.model.request;

import lombok.Data;
import sustech.dbojbackend.model.SqlLanguage;

import java.io.Serializable;

@Data
public class CommitQuery implements Serializable {
    public String Token;
    public Long questionId;
    public SqlLanguage language;
    /**
     * 0 to Run, unzero value means use table i to test
     */
    public Integer testOrRun;
    public String commitCode;
}
