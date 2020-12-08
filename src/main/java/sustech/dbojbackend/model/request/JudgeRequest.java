package sustech.dbojbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.dbojbackend.model.SqlLanguage;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgeRequest implements Serializable {
    private static final long serialVersionUID = 45033182102L;
    private SqlLanguage language;
    private String createTable;
    private String searchTable;
    private Long LimitTime;
    private Long limitMemory;
}
