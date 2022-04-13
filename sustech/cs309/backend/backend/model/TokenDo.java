package backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDo implements Serializable {
    @Serial
    private static final long serialVersionUID = 4349190260L;
    private int state;
    private String tokenId;
}