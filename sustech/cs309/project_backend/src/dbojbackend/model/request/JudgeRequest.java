package dbojbackend.model.request;

import dbojbackend.model.SqlLanguage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgeRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 45033182102L;
    private SqlLanguage language;
    private String createTable;
    private String searchTable;
    private Long LimitTime;
    private Long limitMemory;
}
