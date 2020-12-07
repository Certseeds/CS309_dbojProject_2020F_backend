package sustech.dbojbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetRequest  implements Serializable {
    private static final long serialVersionUID = 2612262102L;
    private String email;
    private String newPassword;
}
