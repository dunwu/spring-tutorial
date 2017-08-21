package me.chongfeng.spring.orm.dao;

import me.chongfeng.spring.orm.entity.User;

/**
 * @author Zhang Peng
 * @date 2017/4/13.
 */
public interface UserDAO {
    User getUserById(int userId);
}
