package sustech.dbojbackend.model.Users.response;

public class ResetResponse {
    private String state;

    public ResetResponse(boolean s) {
        if(s){
            this.state="success";
        }
        else{
            this.state="failed";
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
