package example.spring.data.nosql.redis;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-10-14
 */
@Service
public class UserServiceImpl implements UserService {

    public static final String DEFAULT_KEY = "spring-boot:user";

    private final RedisTemplate<String, Object> redisTemplate;

    public UserServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
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

    @Override
    public void batchSetUsers(Map<String, User> users) {
        redisTemplate.opsForHash().putAll(DEFAULT_KEY, users);
    }

}
