package example.spring.data.nosql.redis.quickstart;

import java.util.Map;

/**
 * 用户服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-10-14
 */
public interface UserService {

    void batchSetUsers(Map<String, User> users);

    long count();

    User getUser(Long id);

    void setUser(User user);

}
