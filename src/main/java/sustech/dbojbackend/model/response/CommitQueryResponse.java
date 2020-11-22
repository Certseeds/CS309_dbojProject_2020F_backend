package sustech.dbojbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import sustech.dbojbackend.model.data.CommitLog;

@Data
@AllArgsConstructor
public class CommitQueryResponse {
    Long questionOrder;
    CommitLog commitLog;
}
