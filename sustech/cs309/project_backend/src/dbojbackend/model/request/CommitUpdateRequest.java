package dbojbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommitUpdateRequest {
    public CommitUpdateQuestion commitUpdateQuestion;
}
