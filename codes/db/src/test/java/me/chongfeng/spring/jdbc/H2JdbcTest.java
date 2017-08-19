/**
 * The Apache License 2.0
 * Copyright (c) 2016 Zhang Peng
 */
package me.chongfeng.spring.jdbc;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试 H2 JDBC 操作
 * @author Zhang Peng
 * @date 2017/4/14.
 */
@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:db/spring-h2.xml" })
public class H2JdbcTest {
    @Autowired
    private ApplicationContext ctx;

    @Test
    public void testConnH2() throws SQLException, IOException {
        MyJdbcTemplate myJdbcTemplate = (MyJdbcTemplate) ctx.getBean("myJdbcTemplate");
        JdbcDemo.execJdbcOper(myJdbcTemplate);
    }
}
