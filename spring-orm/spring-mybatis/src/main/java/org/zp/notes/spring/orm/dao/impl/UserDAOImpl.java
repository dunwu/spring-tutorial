/**
 * The Apache License 2.0
 * Copyright (c) 2016 victor zhang
 */
package org.zp.notes.spring.orm.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zp.notes.spring.orm.dao.UserDAO;
import org.zp.notes.spring.orm.entity.User;
import org.zp.notes.spring.orm.mapper.UserMapper;

/**
 * @author victor zhang
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
