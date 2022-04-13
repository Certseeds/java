package dbojbackend.model.data;

import dbojbackend.model.CommitResultType;
import dbojbackend.model.SqlLanguage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COMMIT_LOG", schema = "public")
public class CommitLog implements Serializable {
    @Serial
    private static final long serialVersionUID = 0x118341025L;
    // TODO, set the FOREIGH KEY connection
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commit_log_id")
    private Long commitLogId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "question_order", nullable = false)
    private Long questionOrder;
    @Column(name = "commit_code", nullable = false)
    private String commitCode;
    @Column(name = "language", nullable = false)
    private SqlLanguage language;
    @Column(name = "state", nullable = false)
    private CommitResultType state;
    @Column(name = "seconds_from_std", nullable = false)
    private Long seconds;

    public CommitLog(Long userId, Long questionOrder,
                     String commitCode, SqlLanguage sql,
                     CommitResultType state) {
        this.userId = userId;
        this.questionOrder = questionOrder;
        this.commitCode = commitCode;
        this.language = sql;
        this.state = state;
        this.seconds = java.time.Instant.now().getEpochSecond();
    }
}