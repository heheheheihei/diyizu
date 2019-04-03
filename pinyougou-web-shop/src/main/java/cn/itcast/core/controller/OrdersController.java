package cn.itcast.core.controller;

import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.OrdersService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.OrderVo;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Reference
    private OrdersService ordersService;
    @RequestMapping("/findAll")
    public List<OrderVo> findAll(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List <OrderVo> all = ordersService.findAll(name);
        //System.out.println(all);
        return all;
    }
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        //保存
        try {
            ordersService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            //e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }
    //查询一个品牌
    @RequestMapping("/findOne")
    public Order findOne(Long id){
        return ordersService.findOne(id);
    }
    //保存
    @RequestMapping("/update")
    public Result update(@RequestBody Order order){
        //保存
        try {

            ordersService.update(order);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            //e.printStackTrace();
            return new Result(false,"修改失败");
        }

    }



}
