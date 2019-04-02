 //控制层 
app.controller('tongjiController' ,function($scope,$controller,$location ,tongjiService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    






	
	//查询实体 
	$scope.findOne=function(){	
		var id = $location.search()['id'];
		if(null == id){
			//点击新增商品过来的 不应该回显
			return;
		}
		//点击修改按钮 过来的 应该回显
		goodsService.findOne(id).success(// 通过商品ID 查询 商品包装对象（商品对象 商品详情对象 库存结果集对象）
			function(response){
				$scope.entity= response;	//保存商品时 $scope.entity 提交

				// 调用处理富文本编辑器：
				editor.html($scope.entity.goodsDesc.introduction);


				
				// 处理图片列表，因为图片信息保存的是JSON的字符串，让前台识别为JSON格式对象
				$scope.entity.goodsDesc.itemImages = JSON.parse( $scope.entity.goodsDesc.itemImages );
				
				// 处理扩展属性:
				$scope.entity.goodsDesc.customAttributeItems = JSON.parse( $scope.entity.goodsDesc.customAttributeItems );
			
				// 处理规格
				$scope.entity.goodsDesc.specificationItems = JSON.parse($scope.entity.goodsDesc.specificationItems);
			
				// 遍历SKU的集合:
				for(var i=0;i<$scope.entity.itemList.length;i++){
					$scope.entity.itemList[i].spec = JSON.parse( $scope.entity.itemList[i].spec );
				}
			}
		);				
	}
	
	$scope.checkAttributeValue = function(specName,optionName){
		var items = $scope.entity.goodsDesc.specificationItems;
		var object = $scope.searchObjectByKey(items,"attributeName",specName);
		if(object != null){
			if(object.attributeValue.indexOf(optionName)>=0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	//保存 
	$scope.save=function(){	
		// 再添加之前，获得富文本编辑器中的内容。
		$scope.entity.goodsDesc.introduction=editor.html();


		var serviceObject;//服务层对象  				
		if($scope.entity.goods.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.flag){
					//重新查询 
		        	alert(response.message);
		        	location.href="goods.html";
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.flag){
					$scope.reloadList();//刷新列表
					$scope.selectIds = [];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
	// $scope.entity={goods:{},goodsDesc:{},itemList:[]}
	
	$scope.uploadFile = function(){
		// 调用uploadService的方法完成文件的上传
		uploadService.uploadFile().success(function(response){
			if(response.flag){
				// 获得url   <img src="{{image_entity.url}}" -->再次发出请求直接获取图片本身
				$scope.image_entity.url =  response.message;//http://...
			}else{
				alert(response.message);
			}
		});
	}
	
	// 获得了image_entity的实体的数据{"color":"褐色","url":"http://192.168.209.132/group1/M00/00/00/wKjRhFn1bH2AZAatAACXQA462ec665.jpg"}
	$scope.entity={goods:{},goodsDesc:{itemImages:[],specificationItems:[]}};
	
	$scope.add_image_entity = function(){
		$scope.entity.goodsDesc.itemImages.push($scope.image_entity);
	}
	
	$scope.remove_iamge_entity = function(index){
		$scope.entity.goodsDesc.itemImages.splice(index,1);
	}
	
	// 查询一级分类列表:
	$scope.selectItemCat1List = function(){
		//商品分类服务层  查询所有商品分类（父ID为0）
		itemCatService.findByParentId(0).success(function(response){//List<ItemCat>
			$scope.itemCat1List = response;//List<ItemCat>
		});


	}
	
	// 查询二级分类列表:
	$scope.$watch("entity.goods.category1Id",function(newValue,oldValue){
		itemCatService.findByParentId(newValue).success(function(response){
			$scope.itemCat2List = response;
		});
	});
	
	// 查询三级分类列表:
	$scope.$watch("entity.goods.category2Id",function(newValue,oldValue){
		itemCatService.findByParentId(newValue).success(function(response){
			$scope.itemCat3List = response;
		});
	});
	
	// 查询模块ID
	$scope.$watch("entity.goods.category3Id",function(newValue,oldValue){
		itemCatService.findOne(newValue).success(function(response){//itemCat对象
			$scope.entity.goods.typeTemplateId = response.typeId;
		});
	});
	//{{entity.goods.typeTemplateId}}
	//$scope.entity = {goods:{catoryId1:11,typeTemplateId:typeId},goodsDesc:.......
	
	// 查询模板下的品牌列表:
	$scope.$watch("entity.goods.typeTemplateId",function(newValue,oldValue){
		// 根据模板ID查询模板的数据
		typeTemplateService.findOne(newValue).success(function(response){
			$scope.typeTemplate = response;
			// 将品牌的字符串数据转成JSON
			$scope.typeTemplate.brandIds = JSON.parse( $scope.typeTemplate.brandIds );
			
			// 将扩展属性的字符串转成JSON
			if($location.search()['id'] == null){
				$scope.entity.goodsDesc.customAttributeItems = JSON.parse( $scope.typeTemplate.customAttributeItems );
			}
			
		});
		
		// 根据模板ID获得规格的列表的数据： 模板ID  List<Map> java
		typeTemplateService.findBySpecList(newValue).success(function(response){
			$scope.specList = response;
		});
	});
	
	$scope.updateSpecAttribute = function($event,name,value){
		// 调用封装的方法判断 勾选的名称是否存在:
		var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,"attributeName",name);
	
		if(object != null){
			// 找到了
			if($event.target.checked){
				object.attributeValue.push(value);
			}else{
				object.attributeValue.splice(object.attributeValue.indexOf(value),1);
			}
			
			if(object.attributeValue.length == 0){
				var idx = $scope.entity.goodsDesc.specificationItems.indexOf(object);
				$scope.entity.goodsDesc.specificationItems.splice(idx,1);
			}
		}else{
			// 没找到
			$scope.entity.goodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]});
		}
	}
	
	// 创建SKU的信息:
	$scope.createItemList = function(){
		// 初始化基础数据:
		$scope.entity.itemList = [{spec:{},price:0,num:9999,status:'0',isDefault:'0'}];
		
		var items = $scope.entity.goodsDesc.specificationItems;
		
		for(var i=0;i<items.length;i++){
			// 
			$scope.entity.itemList = addColumn($scope.entity.itemList,items[i].attributeName,items[i].attributeValue);
		}
	}
	
	addColumn = function(list,columnName,columnValues){
		// 定义一个集合用于保存生成的每行的数据:
		var newList = [];
		// 遍历该集合的数据:
		for(var i=0;i<list.length;i++){
			var oldRow = list[i];
			for(var j=0;j<columnValues.length;j++){
				// 对oldRow数据进行克隆:
				var newRow = JSON.parse( JSON.stringify(oldRow) );
				newRow.spec[columnName]=columnValues[j];
				// 将newRow存入到newList中
				newList.push(newRow);
			}
			
		}
		
		return newList;
	}
	
	// 显示状态
	$scope.status = ["未审核","审核通过","审核未通过","关闭"];
	
	///$scope.itemCatList = [null,图书、音像、电子书刊,电子书刊,.......   戒指];//1000+
	      //                 0          1             2  ....         1004
    $scope.itemCatList = [];
	// 显示分类:
    $scope.findItemCatList = function(){
        itemCatService.findAll().success(function(response){//List<ItemCat>
            for(var i=0;i<response.length;i++){

                $scope.itemCatList[response[i].id] = response[i].name;
            }
        });
    }











});	
