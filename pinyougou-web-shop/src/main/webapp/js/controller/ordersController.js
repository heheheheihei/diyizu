 //控制层 
app.controller('ordersController' ,function($scope,$controller,$location,ordersService){
	
	$controller('baseController',{$scope:$scope});//继承

    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		ordersService.findAll().success(
			function(response){
				$scope.list=response;
			}
		);
	}
    //批量删除
    $scope.dele=function(){
        //获取选中的复选框
        ordersService.dele( $scope.selectIds ).success(
            function(response){
                if(response.flag){
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }



    // 显示状态
    $scope.status = ["未发货","已发货","未收到货","已收到货"];




	// 查询一个:
    $scope.findByOrderId = function(id){
        ordersService.findOne(id).success(function(response){
            // {id:xx,name:yy,firstChar:zz}
            $scope.entity = response;
        });
    }



    // 修改信息的方法:
    $scope.save = function(){

        ordersService.update($scope.entity).success(function(response){
            // {flag:true,message:xxx}
            // 判断保存是否成功:
            if(response.flag){
                // 保存成功
                alert(response.message);
                $scope.reloadList();
            }else{
                // 保存失败
                alert(response.message);
            }
        });
    }


    //分页
    $scope.findPage=function(page,rows){
        alert(111);
        ordersService.findPage(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }





});	
