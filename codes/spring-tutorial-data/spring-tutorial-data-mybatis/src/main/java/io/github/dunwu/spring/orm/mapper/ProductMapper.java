package io.github.dunwu.spring.orm.mapper;

import io.github.dunwu.spring.orm.entity.Product;
import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    Product selectByPrimaryKey(Integer id);

    List<Product> selectAll();

    int updateByPrimaryKey(Product record);
}