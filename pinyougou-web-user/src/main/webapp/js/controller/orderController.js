//用户中心订单管理控制层（郜金锋负责操作！）
app.controller('orderController',function($scope,$controller,ordersService){

    $controller('indexController',{$scope:$scope});

    // 付款方式选择
    $scope.orderPayType = ["微信","支付宝","银联","其他"];

    $scope.findPersonMsg=function () {
        ordersService.findPersonMsg().success(
            function (response) {
                $scope.entity=response;
            }
        )
    };

    //回显一个订单信息详情
    $scope.findOneOrderDetail=function (orderId) {
        ordersService.findOneOrderDetail(orderId).success(
            function (response) {
                $scope.entity=response;
            }
        )
    };

    //查询所有地址列表
    $scope.findOrderList=function(status){
        ordersService.findOrderList(status).success(
            function(response){
                $scope.orderVoList=response;
            }
        );
    };

});