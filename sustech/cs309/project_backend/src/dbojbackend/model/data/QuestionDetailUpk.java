package dbojbackend.model.data;

import dbojbackend.model.SqlLanguage;
import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class QuestionDetailUpk implements Serializable {
    @Serial
    private static final long serialVersionUID = 0x101228210L;
    private Long programOrder;
    private SqlLanguage language;
}