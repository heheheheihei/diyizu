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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderVo implements Serializable {
    private Long orderId;
    private BigDecimal payment;
    private String status;
    private Date paymentTime;
    private String receiverAreaName;
    private String receiverMobile;
    private String receiver;
    private String title;
    private String sellerId;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getReceiverAreaName() {
        return receiverAreaName;
    }

    public void setReceiverAreaName(String receiverAreaName) {
        this.receiverAreaName = receiverAreaName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "OrderVo{" +
<<<<<<< HEAD
                "order=" + order +
                ",orderItemList=" + orderItemList +
                ",num=" + num +
                '}';
    }

=======
                "orderId=" + orderId +
                ", payment=" + payment +
                ", status='" + status + '\'' +
                ", paymentTime=" + paymentTime +
                ", receiverAreaName='" + receiverAreaName + '\'' +
                ", receiverMobile='" + receiverMobile + '\'' +
                ", receiver='" + receiver + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
>>>>>>> origin/master
}
