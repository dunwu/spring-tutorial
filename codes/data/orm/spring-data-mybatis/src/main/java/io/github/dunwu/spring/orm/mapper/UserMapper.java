package io.github.dunwu.spring.orm.mapper;

import io.github.dunwu.spring.orm.entity.User;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

}
