package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.order.OrderItem;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class TongjiServiceImpl implements TongjiService {
    @Autowired
    private OrderItemDao orderItemDao;
    @Override
    public List<OrderItem> findAll() {

        return orderItemDao.selectByExample(null);
    }
}
