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
 * 测试 Mysql JDBC 操作
 * 注：执行本测试例前，必须先成功执行sql/mysql/create_table_student.sql
 * @author Zhang Peng
 * @date 2017/4/14.
 */
@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:db/spring-mysql.xml" })
public class MysqlJdbcTest {
	@Autowired
	private ApplicationContext ctx;

	@Test
	public void testConnMysql() throws SQLException, IOException {
		MyJdbcTemplate myJdbcTemplate = (MyJdbcTemplate) ctx.getBean("myJdbcTemplate");
		JdbcDemo.execJdbcOper(myJdbcTemplate);
	}
}
