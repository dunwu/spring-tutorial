package io.github.dunwu.springboot.data.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.dunwu.springboot.data.entity.User;

@DS("db2")
public interface Db2UserMapper extends BaseMapper<User> {

}
