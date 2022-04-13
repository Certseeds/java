package dbojbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
@AllArgsConstructor
public class CommitCreateQuestion implements Serializable {
    @Serial
    private static final long serialVersionUID = 7191224202L;
    private String questionTitle;
    private String description;
    private Long deadline;
}