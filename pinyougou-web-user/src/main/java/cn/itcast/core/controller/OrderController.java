package cn.itcast.core.controller;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.OrderVo;

import java.util.List;

/**
 * 用户个人中心：订单管理
 * 描述：用户订单中心仅有查看权限，无法修改！！！
 * @author ：Mr.Gao
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Reference
    OrderService orderService;

    /**
     * 查询当前登录用户基本信息
     * @serialData 2019.04.02
     * @author Mr.Gao
     */
    @RequestMapping("/findPersonMsg")
    public User findPersonMsg() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("1、Order：回显查询");
        return orderService.findPersonMsg(name);
    }

    /**
     * 查询当前登录用户的商品订单详情
     * @param orderId 订单id
     * @return 订单详情对象
     * @serialData 2019.04.02
     * @author Mr.Gao
     */
    @RequestMapping("/findOneOrderDetail")
    public Order findOneOrderDetail(Long orderId) {
        System.out.println("2、Order：回显查询");
        return orderService.findOneOrderDetail(orderId);
    }

    /**
     * 查询当前登录用户的订单列表(使用缓存)
     * @return 订单列表项
     * @serialData 2019.04.02
     * @author Mr.Gao
     */
    @RequestMapping("/findOrdersList")
    public List<OrderVo> findOrdersList(String status) {
        System.out.println("3、Order：查询所有");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderService.findOrdersList(name, status);
    }

}
