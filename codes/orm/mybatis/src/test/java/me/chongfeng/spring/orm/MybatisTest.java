/**
 * The Apache License 2.0
 * Copyright (c) 2016 Zhang Peng
 */
package me.chongfeng.spring.orm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import me.chongfeng.spring.orm.dao.UserDAO;
import me.chongfeng.spring.orm.entity.User;

/**
 * @author Zhang Peng
 * @date 2017/4/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:mybatis/mybatis-spring.xml"})
public class MybatisTest {
    private static Logger logger = LoggerFactory.getLogger(MybatisTest.class);

    @Autowired
    private UserDAO userDAO;

    @Test
    public void test1() {
        User user = userDAO.getUserById(1);
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info("读取user表结果：{}", mapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            Assert.fail(e.getMessage());
        }
    }
}
