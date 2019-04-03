//用户中心地址管理服务层（郜金锋负责操作！）
app.service('ordersService',function($http) {

    //必要的说明（下面的this不能替换成[$http]，只能使用this进行声明，否则前端会报非函数的错误）

    //（S）查询登录用户基本信息
    this.findPersonMsg = function () {
        return $http.get('/orders/findPersonMsg.do');
    };

    //（S）回显查询一个用户地址
    this.findOneOrderDetail = function (id) {
        alert(id);
        return $http.get('/orders/findOneOrderDetail.do?id=' + id);
    };

    //（S）查询购物车列表
    this.findOrderList = function (status) {
        return $http.get('/orders/findOrdersList.do?status=' + status);
    };

});
