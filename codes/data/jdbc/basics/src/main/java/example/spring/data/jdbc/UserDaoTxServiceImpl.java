package example.spring.data.jdbc;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 事务服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDaoTxServiceImpl implements UserDaoTxService {

    private final UserDao userDao;
    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    @Override
    public void noTransaction(User entity) {
        userDao.insert(entity);
        log.info("插入一条记录: {}", JSONUtil.toJsonStr(entity));
        throw new RuntimeException("强制异常");
    }

    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public void withTransaction(User entity) {
        userDao.insert(entity);
        log.info("插入一条记录: {}", JSONUtil.toJsonStr(entity));
        throw new RollbackException("强制异常");
    }

    @Override
    public void withTransaction2(User entity) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                jdbcTemplate.update("INSERT INTO `t_user`(`name`, `age`, `address`, `email`) VALUES(?, ?, ?, ?)",
                    entity.getName(), entity.getAge(), entity.getAddress(), entity.getEmail());
                log.info("写入一条记录：{}", entity);
                status.setRollbackOnly();
            }
        });
    }

    static class RollbackException extends RuntimeException {

        public RollbackException(String message) {
            super(message);
        }

    }

}
