package sustech.dbojbackend.model.Users.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetRequest {
    public String userName;
    public String oldPassword;
    public String newPassword;
    public String token;
}
