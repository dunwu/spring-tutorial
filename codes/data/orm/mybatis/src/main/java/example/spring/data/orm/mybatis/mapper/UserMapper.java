package example.spring.data.orm.mybatis.mapper;

import example.spring.data.orm.mybatis.entity.User;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

}
