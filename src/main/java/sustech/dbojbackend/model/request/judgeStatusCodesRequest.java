package sustech.dbojbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class judgeStatusCodesRequest implements Serializable {
    private static final long serialVersionUID = 0x33731222210202L;
    private String username;
    private Long handInId;
}
