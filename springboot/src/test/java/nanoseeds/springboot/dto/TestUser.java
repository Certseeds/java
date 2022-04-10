package nanoseeds.springboot.dto;

import nanoseeds.springboot.dao.UserDao;
import nanoseeds.springboot.service.logService;
import nanoseeds.springboot.vo.dao.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureDataJpa
public class TestUser {
    private final UserDao userDao;

    @Autowired
    public TestUser(UserDao userDao) {
        this.userDao = userDao;
    }

    @Test
    public void testFindALl() {
        logService.get().info("List All users");
        final List<User> temp = userDao.findAll();
        temp.forEach(x -> System.out.printf("%s\n", x));
        System.out.printf("User numbers %d\n", temp.size());
        userDao.findByLogin("Certseed").forEach(System.out::println);
        Assertions.assertEquals(1, userDao.findByLogin("Certseeds").size());
        Assertions.assertEquals(1, userDao.findUsersByUsername("Killer_Quinn").size());
    }
}
