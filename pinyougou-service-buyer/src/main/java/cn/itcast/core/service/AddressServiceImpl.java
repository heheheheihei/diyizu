package cn.itcast.core.service;

import cn.itcast.core.dao.address.AddressDao;
import cn.itcast.core.dao.address.AreasDao;
import cn.itcast.core.dao.address.CitiesDao;
import cn.itcast.core.dao.address.ProvincesDao;
import cn.itcast.core.pojo.address.*;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收货地址管理
 * developer：Mr.Gao
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private ProvincesDao provincesDao;
    @Autowired
    private CitiesDao citiesDao;
    @Autowired
    private AreasDao areasDao;

    @Override
    public void addNewAddress(Address address) {
        if (null != address){

            addressDao.insertSelective(address);
        }
        System.out.println("1、Address：用户成功添加地址");
    }

    //删除一条用户地址
    @Override
    public void delOneAddress(Long id) {
        addressDao.deleteByPrimaryKey(id);
        System.out.println("2、Address：@_id编号为 " + id + " 的用户地址已被成功删除！");
    }

    @Override
    public void updateAddress(Address address) {
        if (null != address){
            addressDao.updateByPrimaryKeySelective(address);
        }
        System.out.println("3、Address：用户成功修改地址");
    }

    //修改当前用户的默认地址
    @Override
    public void changeStatus(Long id, String username) {
        //查询获取原先默认地址列表并重置为“非默认状态”
        AddressQuery addressQuery = new AddressQuery();
        AddressQuery.Criteria criteria = addressQuery.createCriteria();
        criteria.andIsDefaultEqualTo("1");
        criteria.andUserIdEqualTo(username);
        List<Address> addresses = addressDao.selectByExample(addressQuery);
        if (null != addresses){
            for (Address address : addresses) {
                address.setIsDefault("0");
                addressDao.updateByPrimaryKeySelective(address);
            }
        }
        //设置默认地址
        Address address = new Address();
        address.setId(id);
        address.setIsDefault("1");
        addressDao.updateByPrimaryKeySelective(address);
        System.out.println("4、Address：改变用户地址默认状态——成功");
    }

    @Override
    public Address findOneAddressUI(Long id) {
        Address address = addressDao.selectByPrimaryKey(id);
        System.out.println("5、Address：回显用户地址信息成功");
        return address;
    }

    @Override
    public List<Address> findListByLoginUser(String name) {
        AddressQuery addressQuery = new AddressQuery();
        addressQuery.createCriteria().andUserIdEqualTo(name);
        System.out.println("6、Address：成功返回当前用户[" + name + "]的地址列表。");
        List<Address> addressList = addressDao.selectByExample(addressQuery);
        for (Address address : addressList) {
            //设置省份名称
            ProvincesQuery provincesQuery = new ProvincesQuery();
            provincesQuery.createCriteria().andProvinceidEqualTo(address.getProvinceId());
            Provinces provinces = provincesDao.selectByExample(provincesQuery).get(0);
            address.setProvinceId(provinces.getProvince());
            //设置市级名称
            CitiesQuery citiesQuery = new CitiesQuery();
            citiesQuery.createCriteria().andCityidEqualTo(address.getCityId());
            Cities cities = citiesDao.selectByExample(citiesQuery).get(0);
            address.setCityId(cities.getCity());
            //设置县区名称
            AreasQuery areasQuery = new AreasQuery();
            areasQuery.createCriteria().andAreaidEqualTo(address.getTownId());
            Areas areas = areasDao.selectByExample(areasQuery).get(0);
            address.setTownId(areas.getArea());
        }
        return addressList;
    }

    @Override
    public List<Provinces> loadAllProvinces() {
        List<Provinces> provinces = provincesDao.selectByExample(null);
        System.out.println("7、Address：获取所有省信息");
        return provinces;
    }

    @Override
    public List<Cities> loadAllCities(String provinceId) {
        CitiesQuery citiesQuery = new CitiesQuery();
        citiesQuery.createCriteria().andProvinceidEqualTo(provinceId);
        List<Cities> cities = citiesDao.selectByExample(citiesQuery);
        System.out.println("8、Address：获取所有市信息");
        return cities;
    }

    @Override
    public List<Areas> loadAllAreas(String cityId) {
        AreasQuery areasQuery = new AreasQuery();
        areasQuery.createCriteria().andCityidEqualTo(cityId);
        List<Areas> areas = areasDao.selectByExample(areasQuery);
        System.out.println("9、Address：获取所有县/区信息");
        return areas;
    }

}
