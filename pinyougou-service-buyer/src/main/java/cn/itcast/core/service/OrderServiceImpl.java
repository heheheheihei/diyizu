package cn.itcast.core.service;

import cn.itcast.common.utils.IdWorker;
import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.log.PayLogDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.dao.user.UserDao;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.pojo.user.UserQuery;
import com.alibaba.dubbo.config.annotation.Service;
import entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import vo.OrderVo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单管理
 * Mr.Gao——用户中心（订单管理）
 * 添加的方法：
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private PayLogDao payLogDao;

    //保存订单
    @Override
    public void add(Order order) {
        //收货人  地址  手机  支付方式
        //根据用户名查询此用户的购物车集合（长度）
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cart").get(order.getUserId());
        //很多订单的金额之和
        long payLogTotal = 0;
        //订单ID集合
        List<Long> ids = new ArrayList<>();
        for (Cart cart : cartList) {
            long id = idWorker.nextId();        //订单ID    分布式ID
            ids.add(id);
            order.setOrderId(id);
            double total = 0;                   //实付金额
            order.setStatus("1");               //支付状态
            order.setCreateTime(new Date());    //订单创建时间
            order.setUpdateTime(new Date());
            order.setSourceType("2");           //订单来源
            order.setSellerId(cart.getSellerId());//商家ID
            //多个订单详情表
            List<OrderItem> orderItemList = cart.getOrderItemList();
            for (OrderItem orderItem : orderItemList) {
                Item item = itemDao.selectByPrimaryKey(orderItem.getItemId());
                orderItem.setId(idWorker.nextId());             //订单详情表ID
                orderItem.setGoodsId(item.getGoodsId());        //商品ID
                orderItem.setOrderId(id);                       //订单ID
                orderItem.setTitle(item.getTitle());            //标题
                orderItem.setPrice(item.getPrice());            //价格
                //总金额  小计
                orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue()*orderItem.getNum()));
                total += orderItem.getTotalFee().doubleValue(); //追加总金额
                orderItem.setPicPath(item.getImage());          //图片
                orderItem.setSellerId(item.getSellerId());      //商家ID
                orderItemDao.insertSelective(orderItem);        //保存
            }
            order.setPayment(new BigDecimal(total));            //设置订单的总金额
            payLogTotal += order.getPayment().longValue();
            orderDao.insertSelective(order);                    //保存订单
        }
        PayLog payLog = new PayLog();                           //日志表  （将上面的所有订单合并起来  一起付钱）
        payLog.setOutTradeNo(String.valueOf(idWorker.nextId()));//ID
        payLog.setCreateTime(new Date());                       //日志产生 时间
        payLog.setTotalFee(payLogTotal*100);                    //总金额
        payLog.setUserId(order.getUserId());                    //用户ID
        payLog.setTradeState("0");                              //支付状态
        //订单集合  [341,32414213,2131412]
        payLog.setOrderList(ids.toString().replace("[","").replace("]",""));
        payLog.setPayType("1");//支付方式
        payLogDao.insertSelective(payLog);
        //保存缓存一份
        redisTemplate.boundHashOps("payLog").put(order.getUserId(),payLog);
        //购物车
        redisTemplate.boundHashOps("cart").delete(order.getUserId());
        System.out.println("1、Order：新增订单完成。");

    }

    @Override
    public User findPersonMsg(String userId) {
        UserQuery userQuery = new UserQuery();
        userQuery.createCriteria().andUsernameEqualTo(userId);
        User user = userDao.selectByExample(userQuery).get(0);
        System.out.println("2、Order：用户信息查询完成。");
        return user;
    }

    @Override
    public Order findOneOrderDetail(Long orderId) {
        System.out.println("3、Order：查询订单详情完成。—— " + orderId);
        return null;
    }

    @Override
    public List<OrderVo> findOrdersList(String name, String status) {
        //创建订单列表集合
        List<OrderVo> orderVoList = new ArrayList<>();
        //根据当前登录用户查询订单
        OrderQuery orderQuery = new OrderQuery();
        OrderQuery.Criteria criteria = orderQuery.createCriteria();
        criteria.andUserIdEqualTo(name);
        if (null != status && !"".equals(status)){
            criteria.andStatusEqualTo(status);
        }
        List<Order> orders = orderDao.selectByExample(orderQuery);
        for (Order order : orders) {
            //创建订单封装对象
            OrderVo orderVo = new OrderVo();
            orderVo.setOrder(order);
            //查询订单的订单项
            OrderItemQuery orderItemQuery = new OrderItemQuery();
            orderItemQuery.createCriteria().andOrderIdEqualTo(order.getOrderId());
            List<OrderItem> orderItems = orderItemDao.selectByExample(orderItemQuery);
            orderVo.setNum(orderItems.size());
            orderVo.setOrderItemList(orderItems);
            //填充集合
            orderVoList.add(orderVo);
        }
        //将查询到的集合加入到缓存中
        redisTemplate.boundHashOps("userOrderList").put(name, orderVoList);
        System.out.println(orderVoList);
        System.out.println("4、Order：查询订单列表成功。" + name + "\t" + status);
        return orderVoList;
    }


}
