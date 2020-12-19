package sustech.dbojbackend.model.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sustech.dbojbackend.model.CommitResultType;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatisticsAnalysisResponse {
    private Long submission;
    private Long ac;
    private Long wa;
    private Long tle;
    private Long mle;
    private Long re;
    public StatisticsAnalysisResponse(Long sub, HashMap<CommitResultType,Long> resultTypeLongHashMap){
        this.submission = sub;
        this.ac = resultTypeLongHashMap.get(CommitResultType.AC);
        this.wa = resultTypeLongHashMap.get(CommitResultType.WA);
        this.tle = resultTypeLongHashMap.get(CommitResultType.TLE);
        this.mle = resultTypeLongHashMap.get(CommitResultType.MLE);
        this.re = resultTypeLongHashMap.get(CommitResultType.RE);
    }
}
