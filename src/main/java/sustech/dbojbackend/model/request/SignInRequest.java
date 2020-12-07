package sustech.dbojbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    private static final long serialVersionUID = 0x35339172102L;
    private String userName;
    private String passWord;
    private String email;
}
