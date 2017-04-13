/**
 * The Apache License 2.0
 * Copyright (c) 2016 victor zhang
 */
package org.zp.notes.spring.orm.dao;

import org.zp.notes.spring.orm.entity.User;

/**
 * @author victor zhang
 * @date 2017/4/13.
 */
public interface UserDAO {
    User getUserById(int userId);
}
