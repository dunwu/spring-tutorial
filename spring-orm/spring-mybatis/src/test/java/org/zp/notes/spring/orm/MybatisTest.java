/**
 * The Apache License 2.0
 * Copyright (c) 2016 Zhang Peng
 */
package org.zp.notes.spring.orm;

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
import org.zp.notes.spring.orm.dao.UserDAO;
import org.zp.notes.spring.orm.entity.User;

import javax.annotation.Resource;

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
