package io.github.dunwu.spring.data.db.redis;

import io.github.dunwu.spring.data.db.redis.bean.UserDTO;

import java.util.List;

/**
 * @author Zhang Peng
 * @date 2017/4/12.
 */
public interface IUserDao {
    /**
     * 新增
     */
    boolean add(UserDTO userDTO);

    /**
     * 批量新增 使用pipeline方式
     */
    boolean add(List<UserDTO> list);

    /**
     * 删除
     */
    void delete(String key);

    /**
     * 删除多个
     */
    void delete(List<String> keys);

    /**
     * 修改
     */
    boolean update(UserDTO userDTO);

    /**
     * 通过key获取
     */
    UserDTO get(String keyId);
}
