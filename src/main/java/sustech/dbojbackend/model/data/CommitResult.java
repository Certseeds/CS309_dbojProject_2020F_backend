package sustech.dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "commit_result")
@IdClass(CommitResultUpk.class)
public class CommitResult {
    private static final long serialVersionUID = 0x251020171857L;
    @Id
    @Column(name = "commit_log_id")
    private Long commitLogId;
    @Id
    @Column(name = "table_order")
    private Long tableOrder;
    @Id
    @Column(name = "commit_result")
    private Long commitResult;
}