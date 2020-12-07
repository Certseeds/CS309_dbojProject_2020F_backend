package sustech.dbojbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.dbojbackend.model.CommitResultType;

import java.io.Serializable;
import java.util.ArrayList;

import static sustech.dbojbackend.model.CommitResultType.AC;
import static sustech.dbojbackend.model.CommitResultType.MLE;
import static sustech.dbojbackend.model.CommitResultType.RE;
import static sustech.dbojbackend.model.CommitResultType.TLE;
import static sustech.dbojbackend.model.CommitResultType.WA;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class infoResponse implements Serializable {
    private static final long serialVersionUID = 0x434582102L;
    private Long questionOrder;
    private CommitResultType[] typenames = {AC, WA, TLE, MLE, RE};
    private Long[] typeValues = {0L, 0L, 0L, 0L, 0L};
    private ArrayList<Long> cputime;
    private ArrayList<Long> memsize;

    public infoResponse(Long questionOrder) {
        this.questionOrder = questionOrder;
        this.cputime = new ArrayList<>();
        this.memsize = new ArrayList<>();
    }
}
