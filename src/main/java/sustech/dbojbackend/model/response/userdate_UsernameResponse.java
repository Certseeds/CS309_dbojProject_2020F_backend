package sustech.dbojbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class userdate_UsernameResponse {
    private String name;
    private String ac;
    private String submit;
    private String username;
    private String scores;
    private String des;
    private int rating;
    private String acpro;
}
