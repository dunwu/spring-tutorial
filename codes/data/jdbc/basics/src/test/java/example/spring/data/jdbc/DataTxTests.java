package example.spring.data.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

/**
 * 声明式事务测试例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-22
 */
@Slf4j
@Rollback
@DisplayName("Spring 事务示例")
@SpringBootTest(classes = { DataJdbcApplication.class })
public class DataTxTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserDaoTxService userDaoTxService;

    @BeforeEach
    public void before() {
        userDao.truncate();
    }

    @Test
    @DisplayName("无事务示例")
    public void noTransaction() {
        User entity = new User("王五", 18, "深圳", "wangwu@163.com");
        try {
            userDaoTxService.noTransaction(entity);
        } catch (Exception e) {
            // do nothing
        }

        User record = userDao.queryByName("王五");
        Assertions.assertNotNull(record);
    }

    @Test
    @DisplayName("声明式事务示例")
    public void withTransaction() {
        User entity = new User("赵六", 18, "深圳", "zhaoliu@163.com");
        try {
            userDaoTxService.withTransaction(entity);
        } catch (Exception e) {
            // do nothing
        }

        User record = userDao.queryByName("赵六");
        Assertions.assertNull(record);
    }

    @Test
    @DisplayName("编程式事务示例")
    public void withTransaction2() {
        User entity = new User("钱七", 20, "南京", "qianqi@163.com");
        try {
            userDaoTxService.withTransaction2(entity);
        } catch (Exception e) {
            // do nothing
        }

        User record = userDao.queryByName("钱七");
        Assertions.assertNull(record);
    }

}
