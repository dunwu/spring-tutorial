package example.spring.data.orm.mybatis.mapper;

import example.spring.data.orm.mybatis.model.Country;
import example.spring.data.orm.mybatis.util.MyMapper;
import org.apache.ibatis.annotations.Select;

public interface CountryMapper extends MyMapper<Country> {

    @Select("select * from country limit 1")
    Country findOne();

}
