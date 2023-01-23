package io.github.dunwu.springboot.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 编程式事务示例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-22
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionDemo {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    public void rollback(User user) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                jdbcTemplate.update("INSERT INTO `user`(`name`, `age`, `address`, `email`) VALUES(?, ?, ?, ?)",
                    user.getName(), user.getAge(), user.getAddress(), user.getEmail());
                log.info("写入一条记录：{}", user);
                status.setRollbackOnly();
            }
        });
    }

}
