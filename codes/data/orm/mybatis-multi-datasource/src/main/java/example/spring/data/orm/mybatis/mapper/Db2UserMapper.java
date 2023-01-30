package example.spring.data.orm.mybatis.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import example.spring.data.orm.mybatis.entity.User;

@DS("db2")
public interface Db2UserMapper extends BaseMapper<User> {

}
