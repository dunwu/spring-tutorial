package example.spring.data.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Rollback
@DisplayName("Spring JDBC 基本 CRUD 示例")
@SpringBootTest(classes = { DataJdbcApplication.class })
public class DataJdbcTests {

    @Autowired
    private UserDao userDao;

    @BeforeEach
    public void before() {
        userDao.truncate();
    }

    @Test
    public void insert() {
        userDao.insert(new User("张三", 18, "北京", "user1@163.com"));
        User linda = userDao.queryByName("张三");
        assertThat(linda).isNotNull();
    }

    @Test
    public void batchInsert() {
        List<User> users = new ArrayList<>();
        users.add(new User("张三", 18, "北京", "user1@163.com"));
        users.add(new User("李四", 19, "上海", "user1@163.com"));
        users.add(new User("王五", 18, "南京", "user1@163.com"));
        users.add(new User("赵六", 20, "武汉", "user1@163.com"));

        userDao.batchInsert(users);
        int count = userDao.count();
        assertThat(count).isEqualTo(4);

        List<User> list = userDao.list();
        Assertions.assertThat(list).isNotEmpty().hasSize(4);
        list.forEach(user -> {
            log.info(user.toString());
        });
    }

    @Test
    public void delete() {
        List<User> users = new ArrayList<>();
        users.add(new User("张三", 18, "北京", "user1@163.com"));
        users.add(new User("李四", 19, "上海", "user1@163.com"));
        users.add(new User("王五", 18, "南京", "user1@163.com"));
        users.add(new User("赵六", 20, "武汉", "user1@163.com"));
        userDao.batchInsert(users);

        userDao.deleteByName("张三");
        User user = userDao.queryByName("张三");
        assertThat(user).isNull();

        userDao.deleteAll();
        List<User> list = userDao.list();
        Assertions.assertThat(list).isEmpty();
    }

    @Test
    public void update() {
        userDao.insert(new User("张三", 18, "北京", "user1@163.com"));
        User oldUser = userDao.queryByName("张三");
        oldUser.setName("张三丰");
        userDao.update(oldUser);
        User newUser = userDao.queryByName("张三丰");
        assertThat(newUser).isNotNull();
    }

}
