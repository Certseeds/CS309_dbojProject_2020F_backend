package sustech.dbojbackend.model.Users.response;

import lombok.Data;

@Data
public class ResetResponse {
    private String state;

    public ResetResponse(boolean s) {
        if (s) {
            this.state = "success";
        } else {
            this.state = "failed";
        }
    }

}
