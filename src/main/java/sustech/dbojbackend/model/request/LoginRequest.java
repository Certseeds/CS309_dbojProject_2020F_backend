package sustech.dbojbackend.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 0x4383124211L;
    private String username;
    private String password;
}
