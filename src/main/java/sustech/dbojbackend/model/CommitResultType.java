package sustech.dbojbackend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum CommitResultType implements Serializable {
    @JsonProperty("AC") AC(0), // pass
    @JsonProperty("WA") WA(1), // wrong answer
    @JsonProperty("TLE") TLE(2), // time too long
    @JsonProperty("MLE") MLE(3), // cost too much memory
    @JsonProperty("RE") RE(4); // Run Time Error

    private int order;

    private CommitResultType(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    @JsonCreator
    public static  CommitResultType fromJson(@JsonProperty("name") String name) {
        return valueOf(name);
    }
}