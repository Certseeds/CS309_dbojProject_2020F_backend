package sustech.dbojbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetRequest {
    private static final long serialVersionUID = 2612262102L;
    private String email;
    private String newPassword;
}
