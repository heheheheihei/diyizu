package cn.itcast.core.service;

import cn.itcast.core.pojo.order.OrderItem;

import java.util.List;

public interface TongjiService {
    List<OrderItem> findAll();
}
