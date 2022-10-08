package io.github.dunwu.springboot.core.data;

import java.util.List;

/**
 * @author Zhang Peng
 * @since 2018-12-15
 */
public interface UserDAO {

    /**
     * 删除所有用户
     */
    void clear();

    /**
     * 新增一个用户
     */
    void create(String name, Integer age);

    /**
     * 根据name删除一个用户高
     */
    void deleteByName(String name);

    /**
     * 获取用户总量
     */
    List<User> list();

}
