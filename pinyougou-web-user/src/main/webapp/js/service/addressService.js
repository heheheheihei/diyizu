//用户中心地址管理服务层（郜金锋负责操作！）
app.service('addressService',function($http) {

    //必要的说明（下面的this不能替换成[$http]，只能使用this进行声明，否则前端会报非函数的错误）

    //（S）新增添加用户地址
    this.addNewAddress = function (entity) {
        return $http.post('/address/addNewAddress.do', entity);
    };

    //（S）删除一条地址信息
    this.delOneAddress = function (id) {
        return $http.get('/address/delOneAddress.do?id=' + id);
    };

    //（S）编辑修改地址信息
    this.updateAddress = function (entity) {
        return $http.post('/address/updateAddress.do', entity);
    };

    //修改状态值
    this.changeStatus = function (id) {
        return $http.get('/address/changeStatus.do?id=' + id);
    };

    //（S）回显查询一个用户地址
    this.findOneAddressUI = function (id) {
        return $http.get('/address/findOneAddressUI.do?id=' + id);
    };

    //（S）查询购物车列表
    this.findAddressList = function () {
        return $http.get('/address/findAddressList.do');
    };

    //回显所有省市区信息
    this.loadAllProvinces = function () {
        return $http.get('/address/loadAllProvinces.do');
    };
    this.loadAllCities = function (newProvinceId) {
        return $http.get('/address/loadAllCities.do?provinceId=' + newProvinceId);
    };
    this.loadAllAreas = function (newCityId) {
        return $http.get('/address/loadAllAreas.do?cityId=' + newCityId);
    };

});
