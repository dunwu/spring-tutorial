package io.github.dunwu.springboot.data;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
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
@SpringBootTest(classes = { DataJdbcApplication.class })
public class DataTxTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserDaoTxService userDaoTxService;

    @Test
    @DisplayName("没有事务的例子")
    public void noTransaction() {
        Integer oldNum = userDao.count();

        try {
            userDaoTxService.noTransaction();
        } catch (Exception e) {
            // do nothing
        }

        Integer newNum = userDao.count();
        Assertions.assertNotEquals(oldNum, newNum);
    }

    @Test
    @DisplayName("有事务的例子")
    public void withTransaction() {
        Integer oldNum = userDao.count();

        try {
            userDaoTxService.withTransaction();
        } catch (Exception e) {
            // do nothing
        }

        Integer newNum = userDao.count();
        Assertions.assertEquals(oldNum, newNum);
    }

}
