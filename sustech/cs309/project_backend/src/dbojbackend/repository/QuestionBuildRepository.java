package dbojbackend.repository;

import dbojbackend.model.SqlLanguage;
import dbojbackend.model.data.QuestionBuild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionBuildRepository extends JpaRepository<QuestionBuild, Long> {
    @Transactional(timeout = 10)
    @Modifying
    Integer deleteByProgramOrder(Long order);

    @Transactional(timeout = 10)
    List<QuestionBuild> findByProgramOrderAndLanguage(
            Long programOrder,
            SqlLanguage language
    );

    @Transactional(timeout = 10)
    List<QuestionBuild> findByProgramOrderAndLanguageAndTableOrder(
            Long programOrder,
            SqlLanguage language,
            Long tableOrder
    );
}
