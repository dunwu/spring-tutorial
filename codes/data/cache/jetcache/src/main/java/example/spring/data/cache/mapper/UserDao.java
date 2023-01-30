package example.spring.data.cache.mapper;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import example.spring.data.cache.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface UserDao {

    void batchInsert(List<User> users);

    Integer count();

    @CacheInvalidate(name = "dunwu:users-", key = "#name")
    int deleteByName(String name);

    void insert(User user);

    List<User> list();

    @Cached(name = "dunwu:users-", key = "#name", expire = 3600)
    User queryByName(String name);

    void recreateTable();

    @CacheUpdate(name = "dunwu:users-", key = "#user.name", value = "#user")
    User update(User user);

    JdbcTemplate getJdbcTemplate();

}
