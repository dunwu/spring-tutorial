package example.spring.data.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试 Druid JDBC 操作
 *
 * @author Zhang Peng
 */
@Slf4j
@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:data/spring-druid.xml" })
public class DruidJdbcTest {

    @Autowired
    @Qualifier("mysqlUserDao")
    private UserDao userDao;

    @Before
    public void before() {
        userDao.recreateTable();
    }

    @Test
    public void insert() {
        userDao.insert(new User("张三", 18, "北京", "user1@163.com"));
        User linda = userDao.queryByName("张三");
        Assertions.assertThat(linda).isNotNull();
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
        Assertions.assertThat(count).isEqualTo(4);

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
        Assertions.assertThat(user).isNull();

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
        Assertions.assertThat(newUser).isNotNull();
    }

}
