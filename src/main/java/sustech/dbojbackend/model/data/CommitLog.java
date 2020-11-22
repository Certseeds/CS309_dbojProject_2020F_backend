package sustech.dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import sustech.dbojbackend.model.SqlLanguage;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
public class CommitLog implements Serializable {
    private static final long serialVersionUID = 0x118341025L;
    // TODO, set the FOREIGH KEY connection
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commitLogId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long questionOrder;
    @Column(nullable = false)
    private String commitCode;
    @Column(nullable = false)
    private SqlLanguage language;

    public CommitLog() {

    }
}