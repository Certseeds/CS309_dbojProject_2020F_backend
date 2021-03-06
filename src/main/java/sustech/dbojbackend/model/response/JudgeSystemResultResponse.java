package sustech.dbojbackend.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.dbojbackend.model.CommitResultType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeSystemResultResponse {
    private String data;
    private Integer time;
    private Integer memory;
    private Integer state;
}
