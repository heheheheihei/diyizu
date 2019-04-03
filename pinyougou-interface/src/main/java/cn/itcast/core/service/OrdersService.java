package cn.itcast.core.service;

import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.order.Order;
import entity.PageResult;
import vo.OrderVo;

import java.util.List;

public interface OrdersService {

    List<OrderVo> findAll(String name);

    void delete(Long[] ids);

    Order findOne(Long id);

    void update(Order order);

}
