/**
 * The Apache License 2.0
 * Copyright (c) 2016 Zhang Peng
 */
package me.chongfeng.spring.orm.dao.impl;

import me.chongfeng.spring.orm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import me.chongfeng.spring.orm.dao.UserDAO;
import me.chongfeng.spring.orm.entity.User;

/**
 * @author Zhang Peng
 * @date 2017/4/13.
 */
@Component
public class UserDAOImpl implements UserDAO {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
