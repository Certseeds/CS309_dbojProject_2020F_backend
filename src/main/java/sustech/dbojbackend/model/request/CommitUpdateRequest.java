package sustech.dbojbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import sustech.dbojbackend.model.data.Question;

@Data
@AllArgsConstructor
public class CommitUpdateRequest {
    public String token;
    public CommitUpdateQuestion commitUpdateQuestion;
}
