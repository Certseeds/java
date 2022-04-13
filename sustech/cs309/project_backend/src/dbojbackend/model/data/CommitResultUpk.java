package dbojbackend.model.data;

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
public class CommitResultUpk implements Serializable {
    @Serial
    private static final long serialVersionUID = 0x251020171856L;
    private Long commitLogId;
    private Long tableOrder;
    private String commitResult_;
}