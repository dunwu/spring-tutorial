package example.spring.data.nosql.redis.quickstart;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户服务实现
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-10-14
 */
@Service
public class UserServiceImpl implements UserService {

    public static final String DEFAULT_KEY = "spring:tutorial:user";

    private final RedisTemplate<String, Object> redisTemplate;

    public UserServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void batchSetUsers(Map<String, User> users) {
        redisTemplate.opsForHash().putAll(DEFAULT_KEY, users);
    }

    @Override
    public long count() {
        return redisTemplate.opsForHash().size(DEFAULT_KEY);
    }

    @Override
    public User getUser(Long id) {
        Object obj = redisTemplate.opsForHash().get(DEFAULT_KEY, id.toString());
        return BeanUtil.toBean(obj, User.class);
    }

    @Override
    public void setUser(User user) {
        redisTemplate.opsForHash().put(DEFAULT_KEY, user.getId().toString(), user);
    }

}
