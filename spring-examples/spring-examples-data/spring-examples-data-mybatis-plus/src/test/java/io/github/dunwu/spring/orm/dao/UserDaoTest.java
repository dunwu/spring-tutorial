package io.github.dunwu.spring.orm.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.dunwu.spring.orm.entity.User;

/**
 * @author Zhang Peng
 * @date 2017/4/13.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:mybatis/mybatis-spring.xml"})
public class UserDaoTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("wangwu");
        user.setAge(25);
        user.setSex(1);
        userDAO.insert(user);
    }
}
