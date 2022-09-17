package io.github.dunwu.springboot.data.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.dunwu.springboot.data.entity.User;

@DS("db1")
public interface Db1UserMapper extends BaseMapper<User> {

}
