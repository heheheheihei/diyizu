package cn.itcast.core.service;

import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.address.Areas;
import cn.itcast.core.pojo.address.Cities;
import cn.itcast.core.pojo.address.Provinces;

import java.util.List;

/**
 * developer：Mr.Gao
 */
public interface AddressService {

    void addNewAddress(Address address);

    void delOneAddress(Long id);

    void updateAddress(Address address);

    void changeStatus(Long id, String username);

    Address findOneAddressUI(Long id);

    List<Address> findListByLoginUser(String name);

    List<Provinces> loadAllProvinces();

    List<Cities> loadAllCities(String provinceId);

    List<Areas> loadAllAreas(String cityId);

}
