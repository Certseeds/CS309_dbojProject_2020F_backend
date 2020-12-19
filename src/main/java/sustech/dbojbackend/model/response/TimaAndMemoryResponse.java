package sustech.dbojbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TimaAndMemoryResponse {
    private Long time; // second
    private Long memory; // kb
}
