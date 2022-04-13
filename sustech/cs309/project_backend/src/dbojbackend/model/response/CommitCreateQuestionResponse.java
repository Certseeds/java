package dbojbackend.model.response;

import dbojbackend.model.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommitCreateQuestionResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 0x2119451124L;
    private Long questionOrder;
    private State state;
}
