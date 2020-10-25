package sustech.dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
public class CommitLog implements Serializable {
    private static final long serialVersionUID = 0x118341025L;
    // TODO, set the FOREIGH KEY connection
    @Id
    @GeneratedValue
    private Long commitLogId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long questionOrder;
    @Column(nullable = false)
    private String commitCode;
    @Column(nullable = false)
    private String language;

    public CommitLog() {

    }
}