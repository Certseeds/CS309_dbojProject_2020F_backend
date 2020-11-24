package sustech.dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.dbojbackend.model.SqlLanguage;
import sustech.dbojbackend.model.State;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(nullable = false)
    private State state;

    public CommitLog(Long userId, Long questionOrder,
                     String commitCode, SqlLanguage sql,
                     State state) {
        this.userId = userId;
        this.questionOrder = questionOrder;
        this.commitCode = commitCode;
        this.language = sql;
        this.state = state;
    }
}