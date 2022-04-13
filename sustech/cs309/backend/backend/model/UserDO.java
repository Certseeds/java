package backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 19290402L;
    private String userName;
    private String passWord;

}
// curl -H "Accept: application/json" \
// -H "Content-type: application/json" \
// -X POST -d '{"userName":"11712312","passWord":"wowawesome"}' localhost:8888/user