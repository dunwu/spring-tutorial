package io.github.dunwu.springboot;

import io.github.dunwu.spring.data.SpringBootDataJdbcMultiDataSourceApplication;
import io.github.dunwu.spring.data.User;
import io.github.dunwu.spring.data.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootDataJdbcMultiDataSourceApplication.class })
public class DataJdbcH2DataSourceTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("h2UserDao")
    private UserDao h2UserDao;

    @Before
    public void before() {
        h2UserDao.recreateTable();
    }

    @Test
    public void insert() {
        h2UserDao.insert(new User("张三", "123456", "user1@163.com"));
        User linda = h2UserDao.queryByName("张三");
        assertThat(linda).isNotNull();
    }

    @Test
    public void batchInsert() {
        List<User> users = new ArrayList<>();
        users.add(new User("张三", "123456", "user1@163.com"));
        users.add(new User("李四", "123456", "user2@163.com"));
        users.add(new User("王五", "123456", "user3@163.com"));
        users.add(new User("赵六", "123456", "user4@163.com"));

        h2UserDao.batchInsert(users);
        int count = h2UserDao.count();
        assertThat(count).isEqualTo(4);

        List<User> list = h2UserDao.list();
        assertThat(list).isNotEmpty().hasSize(4);
        list.forEach(user -> log.info(user.toString()));
    }

    @Test
    public void delete() {
        List<User> users = new ArrayList<>();
        users.add(new User("张三", "123456", "user1@163.com"));
        users.add(new User("李四", "123456", "user2@163.com"));
        users.add(new User("王五", "123456", "user3@163.com"));
        users.add(new User("赵六", "123456", "user4@163.com"));
        h2UserDao.batchInsert(users);

        h2UserDao.deleteByName("张三");
        User user = h2UserDao.queryByName("张三");
        assertThat(user).isNull();

        h2UserDao.deleteAll();
        List<User> list = h2UserDao.list();
        assertThat(list).isEmpty();
    }

    @Test
    public void update() {
        h2UserDao.insert(new User("张三", "123456", "user1@163.com"));
        User oldUser = h2UserDao.queryByName("张三");
        oldUser.setUsername("张三丰");
        h2UserDao.update(oldUser);
        User newUser = h2UserDao.queryByName("张三丰");
        assertThat(newUser).isNotNull();
    }

}
