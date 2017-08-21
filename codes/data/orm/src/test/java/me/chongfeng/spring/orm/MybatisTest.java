package me.chongfeng.spring.orm;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import me.chongfeng.spring.orm.dao.UserDAO;
import me.chongfeng.spring.orm.entity.User;

/**
 * @author Zhang Peng
 * @date 2017/4/13.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:mybatis/mybatis-spring.xml"})
public class MybatisTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testGetUserById() {
        User user = userDAO.getUserById(1);
        Assert.assertNull(user);
    }
}
