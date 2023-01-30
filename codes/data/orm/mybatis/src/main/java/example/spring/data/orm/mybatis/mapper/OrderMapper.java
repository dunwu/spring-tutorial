package example.spring.data.orm.mybatis.mapper;

import example.spring.data.orm.mybatis.entity.Order;

import java.util.List;

public interface OrderMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

}
