package backend.repository;

import backend.model.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TokenRepository extends CrudRepository<Token, Long> {
    @Transactional(timeout = 10)
    Boolean existsByTokenId(String tokenId);

    @Transactional(timeout = 10)
    Token findFirstByTokenId(String tokenId);

}
