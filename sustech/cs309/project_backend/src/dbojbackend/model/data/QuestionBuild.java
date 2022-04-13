package dbojbackend.model.data;

import dbojbackend.model.SqlLanguage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question_build", schema = "public")
public class QuestionBuild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_table_order", nullable = false)
    private Long questionTableOrder;
    @Column(name = "program_order", nullable = false)
    private Long programOrder;
    @Column(name = "build_script", nullable = false)
    private String buildScript;
    @Column(name = "language", nullable = false)
    private SqlLanguage language;
    @Column(name = "table_order", nullable = false)
    private Long tableOrder;
}
