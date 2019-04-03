package vo;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * 订单和订单项的包装类
 * @author mr.Gao
 */
public class OrderVo implements Serializable {

    //当前订单
    private Order order;
    //订单中的订单项
    private List<OrderItem> orderItemList;
    //订单项数目
    private int num;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "OrderVo{" +
                "order=" + order +
                ",orderItemList=" + orderItemList +
                ",num=" + num +
                '}';
    }

}
