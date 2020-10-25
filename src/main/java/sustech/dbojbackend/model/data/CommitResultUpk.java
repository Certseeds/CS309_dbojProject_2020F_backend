package sustech.dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class CommitResultUpk implements Serializable {
    private static final long serialVersionUID = 0x251020171856L;
    private Long commitLogId;
    private Long tableOrder;
    private Long commitResult;
}