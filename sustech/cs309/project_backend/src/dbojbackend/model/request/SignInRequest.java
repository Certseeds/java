package dbojbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 0x35339172102L;
    private String username;
    private String password;
    private String email;
}
