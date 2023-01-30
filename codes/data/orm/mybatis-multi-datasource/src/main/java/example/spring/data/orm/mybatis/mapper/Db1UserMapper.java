package example.spring.data.orm.mybatis.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import example.spring.data.orm.mybatis.entity.User;

@DS("db1")
public interface Db1UserMapper extends BaseMapper<User> {

}
