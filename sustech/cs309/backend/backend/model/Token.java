package backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author nanos
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "token_table")
public class Token implements Serializable {
    @Serial
    private static final long serialVersionUID = 4349190261L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auto_Id", nullable = false)
    private Long autoId;
    @Column(name = "token_id", nullable = false)
    private String tokenId;
    @Column(name = "token_second", nullable = false)
    private Long tokenSecond;

    public Token(String tokenId, Long tokenSecond) {
        this.tokenId = tokenId;
        this.tokenSecond = tokenSecond;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Token token = (Token) o;
        return autoId != null && Objects.equals(autoId, token.autoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(autoId, tokenId, tokenSecond);
    }
}