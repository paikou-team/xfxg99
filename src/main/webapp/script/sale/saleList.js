var m_sale_query={};
var m_sale_dlg;
var m_sale_obj = {};
$(function () {
	var args = getUrlArgs();
	
    $("#cmbSaleDept").combobox({
        valueField: 'id',
        textField: 'name',
        editable: false,
        panelHeight: 'auto',
        onSelect: function (record) { m_sale_query.orgId = record.id; }
    });
	

	setSaleQueryTime();
	loadOrgs();
    
    packQuery();
    
	$('#dgSale').datagrid({
		url:'sale/loadSaleList.do',
		queryParams : {
			'saleQuery' : JSON.stringify(m_sale_query)
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
        onDblClickRow: onSelRow,
        columns: [[
               { title: 'id', field: 'id', align: 'left', width: 5, hidden: true },
               { title: 'orgId', field: 'orgId', align: 'left', width: 5, hidden: true },
               { title: 'custId', field: 'custId', align: 'left', width: 5, hidden: true },
               { title: '单据号', field: 'serialNo', align: 'left', width: 150 },
               { title: '销售部门', field: 'orgName', align: 'left', width: 150 },
               { title: '客户名称', field: 'customerName', align: 'left', width: 100 },
               { title: '金额', field: 'goodsAmount', align: 'right', width: 120 },
               { title: '销售日期', field: 'saleTime', align: 'left', width: 100 },
               { title: '录入日期', field: 'recTime', align: 'left', width: 100 }
        ]]
    });
});


function loadOrgs(){
//	var orgs=loadStockOrg();
	var orgs =  loadAllOrg();
	var a={id:0,name:'全部'};
	orgs.splice(0, 0, a );
	$("#cmbSaleDept").combobox('loadData',orgs);
	
	$("#cmbSaleDept").combobox('select',0);
}

function setSaleQueryTime(){
	var d=getCurrentTime();
	
	var bt=new Date(d);
	bt.add('d',-7);
	var et=new Date(d);
	
	$("#dteBeginTime").datebox('setValue',bt.toSimpleString());
	$("#dteEndTime").datebox('setValue',et.toSimpleString());
}


function onSelRow(rowIndex, rowData){ 
	SaleManage.packageObject(rowData);
	SaleManage.ShowDialog();
}

function loadSaleBills() {

    try {
        packQuery();
        var json = JSON.stringify(m_sale_query);
        $('#dgSale').datagrid('reload', { 'saleQuery': json });

    } catch (ex) {
        alert(ex);
    }
}

function packQuery(){
	m_sale_query.beginTime = $('#dteBeginTime').datebox('getValue');
	m_sale_query.endTime = $('#dteEndTime').datebox('getValue');
	m_sale_query.serialNo = $('#txtSerialNo').val();
	m_sale_query.saletype = 1;
}

function onSaleSearch(){
	loadSaleBills();
}

function onSaleBillAdd(){
	try {
		m_sale_dlg = art.dialog({
            id: 'dlgSaleBillView',
            title: '单据',
            content: "<iframe scrolling='yes' frameborder='0' src='view/sale/saleBill.jsp?optType=0&billType=2&id=0' style='width:760px;height:460px;'/>",
            //content:"123",
            lock: true,
            initFn: function () {
            }
        });
    } catch (ex) {
        alert(ex);
    }
}


function onSaleBillShow(){
	var hasRows = $('#dgSale').datagrid('getRows');
	if (hasRows.length == 0) {
		$.messager.alert('操作提示', "没有可操作数据", "warning");
		return;
	}
	var target = $("#dgSale").datagrid("getChecked");
	if (!target || target.length == 0) {
		$.messager.alert('操作提示', "请选择操作项!", "warning");
		return;
	}
	if (target.length > 1) {
		$.messager.alert('操作提示', "只能选择单个操作项!", "warning");
		return;
	}
	SaleManage.packageObject(target[0]);
	SaleManage.ShowDialog();
}

var SaleManage = {
		packageObject:function(obj){
			m_sale_obj.id = obj.id;
			m_sale_obj.orgId = obj.orgId;
			m_sale_obj.custId = obj.custId;
			m_sale_obj.serialNo = obj.serialNo;
			m_sale_obj.orgName = obj.orgName;
			m_sale_obj.customerName = obj.customerName;
			m_sale_obj.goodsAmount = obj.goodsAmount;
			m_sale_obj.saleTime = obj.saleTime;
			m_sale_obj.recTime = obj.recTime;
		},
		
		ShowDialog:function(obj){
			try {
				m_sale_dlg = art.dialog({
		            id: 'dlgSaleBillView',
		            title: '单据',
		            content: "<iframe scrolling='yes' frameborder='0' src='view/sale/saleBill.jsp?optType=1&billType=2&id="+m_sale_obj.id+"' style='width:760px;height:460px;'/>",
		            //content:"123",
		            lock: true,
		            initFn: function () {
		            }
		        });
		    } catch (ex) {
		        alert(ex);
		    }
		}
};




