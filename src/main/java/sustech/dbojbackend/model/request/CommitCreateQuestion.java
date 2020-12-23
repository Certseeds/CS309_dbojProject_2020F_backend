package sustech.dbojbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
public class CommitCreateQuestion implements Serializable {
    private static final long serialVersionUID = 7191224202L;
    private String questionTitle;
    private String description;
    private Long deadline;
}