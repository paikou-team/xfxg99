var m_inventory_query={};
var m_inventory_user;
var m_inventory_orgId ;
$(function () {
	//var args = getUrlArgs();
	
	m_inventory_user = getCurrentUser();
	var args = getUrlArgs();
	m_inventory_query.orgId = args.orgId;
	m_inventory_orgId = args.orgId;
	//loadOrgs();
	//setDept();
    packQuery();

    $("#cmbDept").combotree({ 
    	url: 'organization/getTreeListByParentId.do?parentid='+m_inventory_orgId
    }); 
	
	$('#dgInventory').datagrid({
		url:'stock/loadInventoryList.do',
		queryParams : {
			'inventoryQuery' : JSON.stringify(m_inventory_query)
		},
        fitColumns: true,
        rownumbers: true,
        resizable: true,
        pagination: true,
        pageNumber: 1,
        pageSize: 20,
        nowrap: false,
        idField: 'id',
        singleSelect: true,
        //onDblClickRow: onSelRow,
        columns: [[
               { title: 'id', field: 'id', align: 'left', width: 5, hidden: true },
               { title: 'orgId', field: 'orgId', align: 'left', hidden: true },
               { title: '部门', field: 'orgName', align: 'left', width: 150 },
               { title: 'goodsId', field: 'goodsId', align: 'left', hidden: true },
               { title: '商品', field: 'goodsName', align: 'left', width: 120 },
               { title: '现有数量', field: 'currentNumber', align: 'left', width: 120},
               { title: '入库数量', field: 'goodsNumber', align: 'right', width: 120,
            	   formatter:function(value,index,orw){
            		   if(value<0){
            			   return '<span style="color:red;">'+value+'</span>'; 
            		   }else{
            			   return value;
            		   }
            	   }},
               { title: '出库数量', field: 'stkOutNumber', align: 'right', width: 120,
            	   formatter:function(value,index,orw){
            		   if(value<0){
            			   return '<span style="color:red;">'+value+'</span>'; 
            		   }else{
            			   return value;
            		   }
            	   }},
               { title: '调拨数量', field: 'stkDbNumber', align: 'right', width: 120,
            	   formatter:function(value,index,orw){
            		   if(value<0){
            			   return '<span style="color:red;">'+value+'</span>'; 
            		   }else{
            			   return value;
            		   }
            	   }},
            	{ title: '总销售量', field: 'saleNumber', align: 'right', width: 120}
        ]]
    });
});

//function setDept(){
//	if(!m_inventory_user.isAllDataPermission){
//		$("#cmbDept").combobox('select',m_inventory_user.orgId);
//	}
//}
//function selectDept(record) {
//	 m_inventory_query.orgId = record.id;
//}
//function loadOrgs(){
//	var orgs=loadStockOrg();
//	var a={id:0,name:'全部'};
//	orgs.splice(0, 0, a );
//	$("#cmbDept").combobox('loadData',orgs);
//	$("#cmbDept").combobox('select',0);
//}

function loadInventory() {
    try {
        packQuery();
        var json = JSON.stringify(m_inventory_query);
        $('#dgInventory').datagrid('reload', { 'inventoryQuery': json });

    } catch (ex) {
        alert(ex);
    }
}

function packQuery(){
	var m_orgId = $("#cmbDept").combotree("getValue");
	if(m_orgId>0){
		m_inventory_query.orgId = m_orgId;
	}else{
		m_inventory_query.orgId = m_inventory_orgId;
	}
	
	m_inventory_query.goodsName=$('#txtGoodsName').val();;
}

function onStockSearch(){
	loadInventory();
}

function onStockBillExport(){
	var rows = $('#dgInventory').datagrid('getRows');
	if(rows.length == 0){
		$.messager.alert("操作提示","无可操作数据","info");
		return;
	} 
    packQuery();
    $.post('stock/exportexcel.do', {
    	inventoryQuery : JSON.stringify(m_inventory_query) 
    }, function(json){ 
		if(json.isSuccess){
			window.location.href = json.path;
		}else{
			$.messager.alert("操作提示",json.Message,"error");
	    }
    });
}


