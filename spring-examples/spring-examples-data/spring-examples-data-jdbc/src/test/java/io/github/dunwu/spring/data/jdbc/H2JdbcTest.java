package io.github.dunwu.spring.data.jdbc;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试 H2 JDBC 操作
 * @author Zhang Peng
 */
@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:db/spring-h2.xml"})
public class H2JdbcTest {

    @Autowired
    private ApplicationContext ctx;

    @Before
    public void before() {
        ctx = JdbcDemo.getH2ApplicationContext();
    }

    @Test
    public void testExecJdbcOper() throws SQLException, IOException {
        MyJdbcTemplateImpl myJdbcTemplate = (MyJdbcTemplateImpl) ctx.getBean("myJdbcTemplate");
        JdbcDemo.execJdbcOper(myJdbcTemplate);
    }

    @After
    public void after() {
        ((ClassPathXmlApplicationContext) ctx).close();
    }
}
