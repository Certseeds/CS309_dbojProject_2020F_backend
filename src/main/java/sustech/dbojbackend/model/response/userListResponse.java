package sustech.dbojbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class userListResponse {
    private static final long serialVersionUID = 0x63342222210202L;
    private Long id;
    private String username;
    private String email;
}
