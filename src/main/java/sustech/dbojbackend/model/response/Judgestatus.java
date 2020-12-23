package sustech.dbojbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.dbojbackend.model.CommitResultType;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Judgestatus implements Serializable {
    private static final long serialVersionUID = -4298842306672275881L;
    private String user;
    private String problem;
    private String table;
    private CommitResultType result;
    private Long time;
    private Long memory;
    private String language;
    private Integer length;
}
