package dbojbackend.repository;

import dbojbackend.model.SqlLanguage;
import dbojbackend.model.data.QuestionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionDetailRepository extends JpaRepository<QuestionDetail, Long> {
    @Transactional(timeout = 10)
    List<QuestionDetail> findByProgramOrderAndLanguage(Long programOrder, SqlLanguage language);

}
