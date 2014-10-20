/**
 * 
 */
var baidumap ="";
$(function () {
    $('#OrganizationTree').treegrid({
//    	url: 'organization/getSortList.do',
        fitColumns: true,
        rownumbers: true,
        resizable: true,
        idField: 'path',
        treeField: 'name',
        toolbar:'#OrganizationTb',
        columns: [[
               { title: '组织机构名称', field: 'name', align: 'left', width: 200 },
               { field: 'id', title: 'Id', width: 130, align: 'center', hidden: true },
               { title: '层级', field: 'level', align: 'right', hidden: false },
               { title: 'parentId', field: 'parentId', align: 'center', hidden: true },
               { title: 'parentName', field: 'parentName', align: 'center', hidden: true },
               { title: 'path', field: 'path', align: 'center', hidden: true },
               { title: 'OrderNo', field: 'OrderNo', align: 'center', hidden: true },
               { title: 'lng', field: 'lng', align: 'center', hidden: true },
               { title: 'lat', field: 'lat', align: 'center', hidden: true },
               { title: 'isStock', field: 'isStock', width: 50, align: 'center' ,hidden: true },
               { title: '联系电话', field: 'phone', align: 'left', width: 200 },
               { title: '地址', field: 'address', align: 'left', width: 200 }

        ]]
    });
    
    loadData();
    
    
    $("#AddOrganization").bind("click", Organization.AddOrganization);
    $("#EditOrganization").bind("click", Organization.EditOrganization);
    $("#DelOrganization").bind("click", Organization.DelOrganization);
//
    $("#SaveInfo").bind("click", SaveInfo);
    $("#CancelInfo").bind("click", CancelInfo);
});

var Organization = {
	    //新增组织机构
	    AddOrganization: function () {
	        ClearForm();
	        var rows = $('#OrganizationTree').treegrid('getSelected');
	        var root = $('#OrganizationTree').treegrid('getRoot');
	        if (root) {
	            if (!rows || rows.length == 0) {
	                $.messager.alert('操作提示',"请选择需增加的上级部门!", "warning"); 
	                return;
	            }
	            $("#txt_Id").val("0");
	            $("#txt_ParentId").val(rows.id);
	            $("#txt_NodeLevel").val(rows.level);
	            $("#txt_FullPath").val(rows.path + ".");
	            $("#txt_ParentName").val(rows.name);
	        }else
	        {
	            $("#txt_Id").val("0");
	            $("#txt_ParentId").val("0");
	            $("#txt_NodeLevel").val("0");
	            $("#txt_FullPath").val("0");
	            $("#txt_ParentName").val("");
	            $("#txt_Address").val("");
	            $("#txt_Phone").val("");
	            $("#txt_lng").val("");
	            $("#txt_lat").val("");
	        }
	        ShowDialog("新增", "div_Organization");
	        //根据ip获得当前城市
	        localCity();
	    },
	    EditOrganization: function () {
	        var rows = $('#OrganizationTree').treegrid('getSelected');
	        if (!rows || rows.length == 0) {
	            $.messager.alert('操作提示', "请选择要操作的数据!", "warning");
	            return;
	        }
	        if (rows.length > 1) {
	            $.messager.alert('操作提示', "只能对单挑数据进行操作!", "warning"); 
	            return;
	        }
	        ClearForm();
	        var Id = rows.id;
	        $("#txt_Id").val(Id);
	        $("#txt_Name").val(rows.name);
	        $("#txt_Name").validatebox('validate');
	        $("#txt_Address").val(rows.address);
	        $("#txt_ParentId").val(rows.parentId);

	        var parentName = findParentName(rows.parentId);
	        $("#txt_ParentName").val(parentName);
	        $("#txt_ParentName").validatebox('validate');
	        
	        $("#txt_NodeLevel").val(rows.level);
	        $("#txt_FullPath").val(rows.path);
	        
	        $("#txt_Phone").val(rows.phone);
	        $("#txt_lng").val(rows.lng);
            $("#txt_lat").val(rows.lat);
	        
	        ShowDialog("编辑", "div_Organization");
	        
	        var lng = rows.lng;
	        var lat = rows.lat;

	        //当记录中有经纬度
	        if(lng !="" && lat !=""){
	        	
//	        	setLocation(lng,lat);
	        	setPlace(lng,lat);
	        }
	        else{
	        	localCity();
	        }
	        
	        document.getElementById("IsStockCheck").checked = rows.isStock;
	        
	    },
	    DelOrganization: function () {
	        var rows = $('#OrganizationTree').treegrid('getSelected');
	        if (!rows || rows.length == 0) {
	            $.messager.alert('操作提示', "请选择要操作的数据!", "warning");
	            return;
	        }
	        if (rows.length > 1) {
	            $.messager.alert('操作提示', "只能对单挑数据进行操作!", "warning"); 
	            return;
	        }
	        deleteRecord(rows.id);
	    }
	};

var DialogForOrganization;
function ShowDialog(dtitle, contentId, selectId, userId) {
	DialogForOrganization = art.dialog({
        title: dtitle,
        content: document.getElementById(contentId),
        lock: true,
        initFn: function () {
        },
        width: 680,
        height: 100
    });
    //loadComBoxData(selectId);
	createMap();
};
function SaveInfo() {
    var OrganizationObj = {};
    OrganizationObj.id = $("#txt_Id").val();
    OrganizationObj.name = $("#txt_Name").val();
    OrganizationObj.address = $("#txt_Address").val();
    OrganizationObj.level = $("#txt_NodeLevel").val();
    OrganizationObj.path = $("#txt_FullPath").val();
    OrganizationObj.parentId = $("#txt_ParentId").val();
    OrganizationObj.phone = $("#txt_Phone").val();
    OrganizationObj.lng = $("#txt_lng").val();
    OrganizationObj.lat = $("#txt_lat").val();
    OrganizationObj.isStock = document.getElementById("IsStockCheck").checked;
    
    $.ajax({
		url :  "organization/saveOrganization.do",
		type : "POST",
		dataType : "json",
		async : false,
		data : OrganizationObj,
		success : function(req) {
			if (req) {
				DialogForOrganization.close();
				loadData();
			} else {
				$.messager.alert('保存记录失败ʾ', req.msg, "warning");
			}
		}
	});
};
function deleteRecord(Id)
{
	$.messager.confirm('删除记录', '确认要删除本借点和该节点所有子节点吗?', function (r) {
        if (r) {
        	$.ajax({
        		url :  "organization/deleteOrg.do?Id="+Id,
        		type : "POST",
        		dataType : "json",
        		async : false,
        		success : function(req) {
        			if (req) {
        				loadData();
        			} else {
        				$.messager.alert('删除记录失败.', '该部门已有用户,不能删除.', "warning");
        			}
        		}
        	});
        }
    });
};
function ClearForm() {
    $("#txt_Id").val("");
    $("#txt_Name").val("");
    $("#txt_Address").val("");
    $("#txt_FullPath").val();
    $("#txt_NodeLevel").val();
    $("#txt_Phone").val("");
    $("#txt_lng").val("");
    $("#txt_lat").val("");
    
    document.getElementById("IsStockCheck").checked = false;
};
function CancelInfo() {
    DialogForOrganization.close();
};

function loadData(){
	$.ajax({
		url : "organization/getList.do",
		type : "POST",
		dataType : "json",
		async : false,
		success : function(req) {
			if (req.isSuccess) {
				var nodes = buildTreeOrg(req.rows);
				$('#OrganizationTree').treegrid("loadData", nodes);
			} 
		}
	});
}
/**
 * 构建组织结构树
 */
function buildTreeOrg(items){
	var ss=[];
	var cache={};
	
	if(items == null || items.length==0){
		return ss;
	}
	
	var count=items.length;
	
	for (var i = 0; i < count; i++) {
		var node=items[i];
		node.name = node.name;
		
		cache[node.id]=node;
		if(node.level==1){
			ss.push(node);
		}else{
			var node2=cache[node.parentId];
			if(node2.children==undefined){
				node2.children=[];
			}
			node2.children.push(node);
		}
	}
	return ss;
}
function findParentName(parentId){
	var result="";
	var roots=$('#OrganizationTree').treegrid('getRoots'),children,i,j;
    for(i=0;i<roots.length;i++){
      children=$('#OrganizationTree').treegrid('getChildren',roots[i].target);
      for(j=0;j<children.length;j++)
    	  if(parentId == children[j].id){
    		  result = children[j].name;
    		  return result;
    	  }
    }
    return result;
}
/**
 * 百度地图
 */
function createMap(){
    var map = new BMap.Map("map");//在百度地图容器中创建一个地图
    var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,12);
	map.enableScrollWheelZoom(true); 
	var geoc = new BMap.Geocoder();  
	baidumap = map;//将map变量存储在全局
	//单击获取点击的经纬度
	map.addEventListener("click",function(e){
		var pt = e.point;
		clickSetPlace(e.point.lng,e.point.lat);
		geoc.getLocation(pt, function(rs){
			var addComp = rs.addressComponents;
			var newaddress = addComp.province  + addComp.city +  addComp.district +addComp.street +  addComp.streetNumber;
			$("#txt_Address").val(newaddress);
			//alert();
		});        
	});
}
/**
 * 根据ip获得当前城市
 */
function localCity(){
	baidumap.clearOverlays();    //清除地图上所有覆盖物

	function myFun(result){
		var cityName = result.name;
		baidumap.setCenter(cityName);
	}
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);       // 设置地图显示的城市 此项是必须设置的
}
/**
 * 根据经纬度切换地图
 */
function setLocation(lng,lat){
	baidumap.clearOverlays(); 
	var new_point = new BMap.Point(lng,lat);
	var marker = new BMap.Marker(new_point);  // 创建标注
	baidumap.addOverlay(marker);              // 将标注添加到地图中
	baidumap.panTo(new_point);      
}
/**
 * 设置城市
 */
function searchAddress(){
//	var city = document.getElementById("cityName").value;
//	if(city != ""){
//		baidumap.centerAndZoom(city,12);      // 用城市名设置地图中心点
//	}
	var address = $("#txt_Address").val();
	var myGeo = new BMap.Geocoder();
	myGeo.getPoint(address, function(point){
		if (point) {
			baidumap.centerAndZoom(point, 16);
			baidumap.clearOverlays();
			baidumap.addOverlay(new BMap.Marker(point));
			$("#txt_lng").val(point.x);
		    $("#txt_lat").val(point.y);
		}else{
			alert("您选择地址没有解析到结果!");
		}
	});
}
/**
 * 设置经纬度标记
 */
function setPlace(lng,lat){
	baidumap.clearOverlays();    //清除地图上所有覆盖物

	var pp = new BMap.Point(lng,lat);   //获取第一个智能搜索的结果
	baidumap.centerAndZoom(pp, 12);
	baidumap.addOverlay(new BMap.Marker(pp));    //添加标注
//	baidumap.setCenter(pp); 
	baidumap.panTo(pp);
	
	
	$("#txt_lng").val(lng);
    $("#txt_lat").val(lat);
}
function clickSetPlace(lng,lat){
	baidumap.clearOverlays();    //清除地图上所有覆盖物
	var pp = new BMap.Point(lng,lat);   //获取第一个智能搜索的结果
//	baidumap.centerAndZoom(pp, 12);
	baidumap.addOverlay(new BMap.Marker(pp));    //添加标注
	baidumap.setCenter(pp); 
	baidumap.panTo(pp);

	$("#txt_lng").val(lng);
    $("#txt_lat").val(lat);
}