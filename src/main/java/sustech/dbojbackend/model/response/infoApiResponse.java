package sustech.dbojbackend.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class infoApiResponse implements Serializable {
    private static final long serialVersionUID = 0x51445132210202L;
    private Long questionId;
    private Long maxValue;
    private ArrayList<Long> statusData = new ArrayList<>();
}
