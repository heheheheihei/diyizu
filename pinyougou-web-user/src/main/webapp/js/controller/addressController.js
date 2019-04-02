//用户中心地址管理控制层（郜金锋负责操作！）
app.controller('addressController',function($scope, addressService){

    //新增&修改（addNewAddress）
    $scope.addAndChangeAddress=function(){
        var object;
        if($scope.entity.id != null){
            //编辑修改地址信息（updateAddress）
            object = addressService.updateAddress($scope.entity);
        }else{
            //新增添加用户地址（addNewAddress）
            object = addressService.addNewAddress($scope.entity);
        }
        object.success(
            function(response){
                if(response.flag){
                    alert(response.message);
                    $scope.findAddressList();
                }else{
                    alert(response.message);
                }
            }
        );
    };
    //回显一个地址信息
    $scope.findOneAddressUI=function (id) {
        addressService.findOneAddressUI(id).success(
            function (response) {
                $scope.entity=response;
            }
        )
    };

    //删除一条地址信息（delOneAddress）
    $scope.delOneAddress=function(id){
        if(confirm("您确认要删除所选项吗？")){
            addressService.delOneAddress(id).success(
                function(response){
                    if(response.flag){
                        alert(response.message);
                        $scope.findAddressList();
                    }else{
                        alert(response.message);
                    }
                }
            );
        }
    };

    //修改状态值（changeStatus）
    $scope.changeStatus=function(id){
        addressService.changeStatus(id).success(
            function(response){
                if(response.flag){
                    alert(response.message);
                    $scope.findAddressList();
                }else{
                    alert(response.message);
                }
            }
        );
    };

    //查询所有地址列表
    $scope.findAddressList=function(){
        addressService.findAddressList().success(
            function(response){
                $scope.addressList=response;
            }
        );
    };

    //加载省级信息列表
    $scope.loadAllProvinces=function(){
        addressService.loadAllProvinces().success(
            function(response){
                $scope.provincesList=response;
            }
        );
    };
    // 监控省级变化，连带查询地区市列表
    $scope.$watch("entity.provinceId",function(newProvinceId,oldValue){
        addressService.loadAllCities(newProvinceId).success(
            function(response){
                $scope.citiesList=response;
            }
        );
    });
    // 监控地级市信息变化，连带查询县/区信息列表
    $scope.$watch("entity.cityId",function(newCityId,oldValue){
        addressService.loadAllAreas(newCityId).success(
            function(response){
                $scope.areasList=response;
            }
        );
    });

});