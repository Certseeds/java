package dbojbackend.model.response;

import dbojbackend.model.CommitResultType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Judgestatus implements Serializable {
    @Serial
    private static final long serialVersionUID = -4298842306672275881L;
    private String user;
    private String problem;
    private String table;
    private CommitResultType result;
    private Long time;
    private Long memory;
    private String language;
    private Integer length;
}
