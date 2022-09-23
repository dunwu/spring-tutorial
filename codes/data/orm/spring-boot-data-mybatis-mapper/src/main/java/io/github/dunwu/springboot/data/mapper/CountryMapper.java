package io.github.dunwu.springboot.data.mapper;

import io.github.dunwu.springboot.data.model.Country;
import io.github.dunwu.springboot.data.util.MyMapper;
import org.apache.ibatis.annotations.Select;

public interface CountryMapper extends MyMapper<Country> {

    @Select("select * from country limit 1")
    Country findOne();

}
