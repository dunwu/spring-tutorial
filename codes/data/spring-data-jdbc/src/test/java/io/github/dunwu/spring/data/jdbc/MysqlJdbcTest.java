/**
 * The Apache License 2.0 Copyright (c) 2016 Zhang Peng
 */
package io.github.dunwu.spring.data.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 测试 Mysql JDBC 操作
 * @author Zhang Peng
 */
@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:data/spring-mysql.xml" })
public class MysqlJdbcTest {

    @Autowired
    private ApplicationContext ctx;

    @Before
    public void before() {
        ctx = JdbcDemo.getMysqlApplicationContext();
    }

    @Test
    public void testExecJdbcOper() throws SQLException, IOException {
        UserDao userDao = (UserDaoImpl) ctx.getBean("userDao");
        JdbcDemo.execJdbcOper(userDao);
    }

    @After
    public void after() {
        ((ClassPathXmlApplicationContext) ctx).close();
    }

}
