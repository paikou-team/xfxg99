var m_inventory_query={};
var m_inventory_user;

$(function () {
	//var args = getUrlArgs();
	
	m_inventory_user = getCurrentUser();
	
	
    $("#cmbDept").combobox({
        valueField: 'id',
        textField: 'name',
        editable: false,
        panelHeight: 'auto',
        onSelect: function (record) { m_inventory_query.orgId = record.id; }
    });
	
	loadOrgs();
	setDept();
    packQuery();
    
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
        pageSize: 10,
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
               { title: '数量', field: 'goodsNumber', align: 'right', width: 120,
            	   formatter:function(value,index,orw){
            		   if(value<0){
            			   return '<span style="color:red;">'+value+'</span>'; 
            		   }else{
            			   return value;
            		   }
            	   }}
        ]]
    });
});

function setDept(){
	if(!m_inventory_user.isAllDataPermission){
		$("#cmbDept").combobox('select',m_inventory_user.orgId);
	}
}

function loadOrgs(){
	var orgs=loadStockOrg();
	var a={id:0,name:'全部'};
	orgs.splice(0, 0, a );
	$("#cmbDept").combobox('loadData',orgs);
	$("#cmbDept").combobox('select',0);
}

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
	m_inventory_query.goodsName=$('#txtGoodsName').val();;
}

function onStockSearch(){
	loadInventory();
}



