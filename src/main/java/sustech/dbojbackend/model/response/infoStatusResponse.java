package sustech.dbojbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.dbojbackend.model.CommitResultType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class infoStatusResponse implements Serializable {
    private static final long serialVersionUID = 0x02021432210202L;
    private Long questionId;
    private ArrayList<infoStatusInsideResponse> statusData = new ArrayList<>();

    public infoStatusResponse(Long questionId, HashMap<CommitResultType, Long> resultTypeLongHashMap) {
        this.questionId = questionId;
        for (var pair : CommitResultType.values()) {
            statusData.add(new infoStatusInsideResponse(pair, resultTypeLongHashMap.get(pair)));
        }
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class infoStatusInsideResponse implements Serializable {
    private Long value;
    private String name;

    public infoStatusInsideResponse(CommitResultType commitResultType, Long v) {
        this.name = commitResultType.name();
        this.value = v;
    }
}