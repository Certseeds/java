package dbojbackend.model.response;

import dbojbackend.model.data.CommitLog;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommitQueryResponse {
    Long questionOrder;
    CommitLog commitLog;
}
