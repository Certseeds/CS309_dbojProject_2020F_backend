package sustech.dbojbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InResponse  implements Serializable {
    private static final long serialVersionUID = 0x348391702L;
    private String username;
    private String token;
}
