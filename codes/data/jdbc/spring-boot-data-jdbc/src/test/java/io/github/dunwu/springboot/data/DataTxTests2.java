package io.github.dunwu.springboot.data;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

/**
 * 编程式事务测试例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-22
 */
@Slf4j
@Rollback
@SpringBootTest(classes = { DataJdbcApplication.class })
public class DataTxTests2 {

    @Autowired
    private TransactionDemo transactionDemo;

    @Test
    public void test() {
        User user = new User("王五", 18, "南京", "user1@163.com");
        transactionDemo.rollback(user);
    }

}
