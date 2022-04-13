package dbojbackend.repository;

import dbojbackend.model.data.CommitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommitLogRepository extends JpaRepository<CommitLog, Long> {
    @Transactional(timeout = 10)
    List<CommitLog> findByCommitLogId(Long id);

    @Transactional(timeout = 10)
    List<CommitLog> findByQuestionOrder(Long questionORder);


    @Transactional(timeout = 10)
    List<CommitLog> findByUserId(Long questionORder);


    @Transactional(timeout = 10)
    List<CommitLog> findByUserIdAndQuestionOrder(Long userId, Long questionOrder);
}
