package dbojbackend.model.response;

import dbojbackend.model.CommitResultType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class infoResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 0x434582102L;
    private Long questionOrder;
    private CommitResultType[] typenames = CommitResultType.values();
    private Long[] typeValues = {0L, 0L, 0L, 0L, 0L};
    private ArrayList<Long> cputime;
    private ArrayList<Long> memsize;

    public infoResponse(Long questionOrder) {
        this.questionOrder = questionOrder;
        this.cputime = new ArrayList<>();
        this.memsize = new ArrayList<>();
    }
}
