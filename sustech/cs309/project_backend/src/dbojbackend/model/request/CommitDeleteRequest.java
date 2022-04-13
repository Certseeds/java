package dbojbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class CommitDeleteRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 0x4542382102L;
    Long questionOrder;
}
