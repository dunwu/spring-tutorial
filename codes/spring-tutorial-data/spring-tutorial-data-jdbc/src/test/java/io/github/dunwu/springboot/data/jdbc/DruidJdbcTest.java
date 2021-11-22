package io.github.dunwu.springboot.data.jdbc;

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
 * 测试 Druid JDBC 操作
 *
 * @author Zhang Peng
 */
@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:data/spring-druid.xml" })
public class DruidJdbcTest {

    @Autowired
    private ApplicationContext ctx;

    @Before
    public void before() {
        ctx = JdbcDemo.getDruidApplicationContext();
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
