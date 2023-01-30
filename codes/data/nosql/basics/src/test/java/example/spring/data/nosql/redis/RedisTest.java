package example.spring.data.nosql.redis;

import example.spring.data.nosql.redis.bean.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhang Peng
 * @since 2017/4/12.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:config/spring-redis.xml" })
public class RedisTest {

    @Autowired
    private IUserDao userDao;

    /**
     * 新增
     */
    @Test
    public void testAddUser() {
        UserDTO UserDTO = new UserDTO();
        UserDTO.setId("user1");
        UserDTO.setName("redis-test");
        boolean result = userDao.add(UserDTO);
        Assertions.assertTrue(result);
    }

    /**
     * 批量新增 普通方式
     */
    @Test
    public void testAddUsers1() {
        List<UserDTO> list = new ArrayList<UserDTO>();
        for (int i = 10; i < 50000; i++) {
            UserDTO UserDTO = new UserDTO();
            UserDTO.setId("UserDTO" + i);
            UserDTO.setName("redis-test" + i);
            list.add(UserDTO);
        }
        long begin = System.currentTimeMillis();
        for (UserDTO UserDTO : list) {
            userDao.add(UserDTO);
        }
        System.out.println(System.currentTimeMillis() - begin);
    }

    /**
     * 批量新增 pipeline方式
     */
    @Test
    public void testAddUsers2() {
        List<UserDTO> list = new ArrayList<UserDTO>();
        for (int i = 10; i < 1500000; i++) {
            UserDTO UserDTO = new UserDTO();
            UserDTO.setId("UserDTO" + i);
            UserDTO.setName("redis-test" + i);
            list.add(UserDTO);
        }
        long begin = System.currentTimeMillis();
        boolean result = userDao.add(list);
        System.out.println(System.currentTimeMillis() - begin);
        Assertions.assertTrue(result);
    }

    /**
     * 修改
     */
    @Test
    public void testUpdate() {
        UserDTO UserDTO = new UserDTO();
        UserDTO.setId("user1");
        UserDTO.setName("new_password");
        boolean result = userDao.update(UserDTO);
        Assertions.assertTrue(result);
    }

    /**
     * 通过key删除单个
     */
    @Test
    public void testDelete() {
        String key = "user1";
        userDao.delete(key);
    }

    /**
     * 批量删除
     */
    @Test
    public void testDeletes() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("UserDTO" + i);
        }
        userDao.delete(list);
    }

    /**
     * 获取
     */
    @Test
    public void testGetUser() {
        String id = "user1";
        UserDTO UserDTO = userDao.get(id);
        Assertions.assertNotNull(UserDTO);
        Assertions.assertEquals(UserDTO.getName(), "redis-test");
    }

    /**
     * 设置userDao
     */
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

}
