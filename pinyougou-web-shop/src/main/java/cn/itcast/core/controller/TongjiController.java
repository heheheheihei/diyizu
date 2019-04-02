package cn.itcast.core.controller;

import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.service.TongjiService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tongji")
public class TongjiController {
    @Reference
    private TongjiService tongjiService;
    @RequestMapping("/findAll")
    public List<OrderItem> findAll(){
        return tongjiService.findAll();
    }
}
