/**
 * The Apache License 2.0
 * Copyright (c) 2016 Zhang Peng
 */
package me.chongfeng.spring.orm.dao;

import me.chongfeng.spring.orm.entity.User;

/**
 * @author Zhang Peng
 * @date 2017/4/13.
 */
public interface UserDAO {
    User getUserById(int userId);
}
