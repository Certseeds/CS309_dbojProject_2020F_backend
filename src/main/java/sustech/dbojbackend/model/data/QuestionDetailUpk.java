package sustech.dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sustech.dbojbackend.model.SqlLanguage;

import javax.persistence.Embeddable;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class QuestionDetailUpk implements Serializable {
    private static final long serialVersionUID = 0x101228210L;
    private Long programOrder;
    private SqlLanguage language;
}