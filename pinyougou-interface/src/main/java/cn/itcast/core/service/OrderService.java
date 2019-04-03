package cn.itcast.core.service;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.user.User;
import vo.OrderVo;

import java.util.List;

public interface OrderService {

    void add(Order order);

    User findPersonMsg(String userId);

    Order findOneOrderDetail(Long orderId);

    List<OrderVo> findOrdersList(String name, String status);
}
