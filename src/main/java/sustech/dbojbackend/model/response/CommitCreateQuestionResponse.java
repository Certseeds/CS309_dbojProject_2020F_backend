package sustech.dbojbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.dbojbackend.model.State;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommitCreateQuestionResponse implements Serializable {
    private static final long serialVersionUID = 0x2119451124L;
    private Long questionOrder;
    private State state;
}
