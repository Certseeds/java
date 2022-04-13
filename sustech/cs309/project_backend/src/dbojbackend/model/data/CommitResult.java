package dbojbackend.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "commit_result")
@IdClass(CommitResultUpk.class)
public class CommitResult {
    @Serial
    private static final long serialVersionUID = 0x251020171857L;
    @Id
    @Column(name = "commit_log_id", nullable = false)
    private Long commitLogId;
    @Id
    @Column(name = "table_order", nullable = false)
    private Long tableOrder;
    @Id
    @Column(name = "commit_result_", nullable = false)
    private String commitResult_;
    @Column(name = "cputime", nullable = false)
    private Long cputime;
    @Column(name = "ramsize", nullable = false)
    private Long ramsize;

}