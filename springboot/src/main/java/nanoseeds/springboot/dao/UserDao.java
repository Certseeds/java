// SPDX-License-Identifier: AGPL-3.0-or-later
package nanoseeds.springboot.dao;

import nanoseeds.springboot.vo.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    @Transactional(timeout = 10)
    List<User> findByLogin(String login);

    @Transactional(timeout = 10)
    List<User> findUsersByUsername(String userName);
}
