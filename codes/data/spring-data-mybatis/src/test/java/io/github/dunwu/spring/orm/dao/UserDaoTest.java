package io.github.dunwu.spring.orm.dao;

import io.github.dunwu.spring.orm.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Zhang Peng
 * @since 2017/4/13.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:mybatis/mybatis-spring.xml" })
public class UserDaoTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("赵四");
        user.setAge(13);
        user.setEmail("xxx@xxx.com");
        user.setAddress("南京");
        try {
            userDAO.insert(user);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Test
    public void testSelect() {
        List<User> users = userDAO.selectAll();
        for (User user : users) {
            System.out.println("user name: " + user.getName());
        }
    }

}
