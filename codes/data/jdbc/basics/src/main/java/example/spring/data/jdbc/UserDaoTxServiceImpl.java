package example.spring.data.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事务服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-22
 */
@Service
@RequiredArgsConstructor
public class UserDaoTxServiceImpl implements UserDaoTxService {

    private final UserDao userDao;

    @Override
    public void noTransaction() {
        userDao.insert(new User("王五", 18, "深圳", "user3@163.com"));
        throw new RuntimeException("强制异常");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withTransaction() {
        userDao.insert(new User("赵六", 18, "深圳", "user3@163.com"));
        throw new RuntimeException("强制异常");
    }

}
