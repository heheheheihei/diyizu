package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import vo.OrderVo;

import java.util.ArrayList;
import java.util.List;
@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;


    @Override
    public List <OrderVo> findAll(String name) {
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.createCriteria().andSellerIdEqualTo(name);
        List <Order> orders = orderDao.selectByExample(orderQuery);

        ArrayList <OrderVo> volist = new ArrayList <>();
        for (Order order : orders) {
            OrderVo orderVo = new OrderVo();
            orderVo.setOrderId(order.getOrderId());
            orderVo.setPayment(order.getPayment());
            orderVo.setPaymentTime(order.getPaymentTime());
            orderVo.setReceiver(order.getReceiver());
            orderVo.setReceiverAreaName(order.getReceiverAreaName());
            orderVo.setReceiverMobile(order.getReceiverMobile());
            orderVo.setStatus(order.getStatus());
            OrderItem orderItem = orderItemDao.selectByPrimaryKey(order.getOrderId());
//            System.out.println(orderItem.getTitle());
            orderVo.setTitle(orderItem.getTitle());
            volist.add(orderVo);

        }


        return volist;


    }

    @Override
    public void delete(Long[] ids) {
        //判断
        if (null != ids && ids.length > 0) {
            for (Long id : ids) {
                orderDao.deleteByPrimaryKey(id);
            }
        }


    }

    @Override
    public Order findOne(Long id) {

        return orderDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(Order order) {

        orderDao.updateByPrimaryKeySelective(order);


    }


}
