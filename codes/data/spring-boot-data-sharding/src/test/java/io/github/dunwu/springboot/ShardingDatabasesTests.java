package io.github.dunwu.springboot;

import io.github.dunwu.springboot.data.entity.User;
import io.github.dunwu.springboot.data.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 分库案例测试
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-10-17
 */
@Rollback
@SpringBootTest
@ActiveProfiles({ "sharding-databases" })
@RunWith(SpringRunner.class)
public class ShardingDatabasesTests {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Before
    public void before() {
        userMapper.delete(null);
    }

    @Test
    public void insert() {

        for (int i = 0; i < 100; i++) {
            userMapper.insert(new User("name" + i, 18, "南京", "xxx@163.com"));
        }
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(100, userList.size());
        log.info("======= 打印 user 表所有数据 =======");
        userList.forEach(user -> {
            log.info(user.toString());
        });
    }

}
