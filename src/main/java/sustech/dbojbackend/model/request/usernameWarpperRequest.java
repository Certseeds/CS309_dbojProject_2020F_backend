package sustech.dbojbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class usernameWarpperRequest implements Serializable {
    private static final long serialVersionUID = 0x81051122210202L;
    private String username;
}
