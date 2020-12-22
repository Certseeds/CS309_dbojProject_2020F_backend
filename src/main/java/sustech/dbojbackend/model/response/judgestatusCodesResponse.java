package sustech.dbojbackend.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class judgestatusCodesResponse implements Serializable {
    private static final long serialVersionUID = 0x63342222210202L;
    private Long commitLogId;
    private Long cputime;
    private Long ramsize;
    private String code;
}
