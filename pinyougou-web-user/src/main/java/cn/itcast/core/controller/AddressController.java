package cn.itcast.core.controller;

import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.address.Areas;
import cn.itcast.core.pojo.address.Cities;
import cn.itcast.core.pojo.address.Provinces;
import cn.itcast.core.service.AddressService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 用户个人中心：地址管理
 * developer：Mr.Gao
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    AddressService addressService;

    //新增一个用户地址
    @RequestMapping("/addNewAddress")
    public Result addNewAddress(@RequestBody Address address) {
        try{
            System.out.println("1、Address：添加地址");
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            address.setUserId(name);
            address.setIsDefault("0");
            address.setCreateDate(new Date());
            addressService.addNewAddress(address);
            return new Result(true,"地址添加成功！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "地址添加失败！");
        }
    }

    //删除当前登录用户的一条地址
    @RequestMapping("/delOneAddress")
    public Result delOneAddress(Long id) {
        try{
            System.out.println("2、Address：删除地址");
            addressService.delOneAddress(id);
            return new Result(true,"地址删除成功！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "地址删除失败！");
        }
    }

    //删除当前登录用户的一条地址
    @RequestMapping("/updateAddress")
    public Result updateAddress(@RequestBody Address address) {
            try{
            System.out.println("3、Address：修改地址");
            addressService.updateAddress(address);
            return new Result(true,"地址修改成功！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "地址修改失败！");
        }
    }

    //修改当前用户的默认地址
    @RequestMapping("/changeStatus")
    public Result changeStatus(Long id) {
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            System.out.println("4、Address：默认地址修改");
            addressService.changeStatus(id, username);
            return new Result(true,"成功修改默认地址！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "默认地址更改失败！");
        }
    }

    //查询当前登录用户的地址列表
    @RequestMapping("/findOneAddressUI")
    public Address findOneAddressUI(Long id) {
        System.out.println("5、Address：回显查询");
        return addressService.findOneAddressUI(id);
    }

    //查询当前登录用户的地址列表
    @RequestMapping("/findAddressList")
    public List<Address> findAddressList() {
        System.out.println("6、Address：查询所有");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return addressService.findListByLoginUser(name);
    }

    //查询地址的省市县信息
    @RequestMapping("/loadAllProvinces")
    public List<Provinces> loadAllProvinces() {
        System.out.println("7、Address：查询省信息");
        return addressService.loadAllProvinces();
    }
    @RequestMapping("/loadAllCities")
    public List<Cities> loadAllCities(String provinceId) {
        System.out.println("8、Address：查询市信息");
        return addressService.loadAllCities(provinceId);
    }
    @RequestMapping("/loadAllAreas")
    public List<Areas> loadAllAreas(String cityId) {
        System.out.println("9、Address：查询县/区信息");
        return addressService.loadAllAreas(cityId);
    }

}
