var m_sale_query={};
var m_sale_dlg;
var m_sale_obj = {};
var m_cashsale_orgId;
var m_cashsale_isdelivery;
$(function () {
	var args = getUrlArgs();
	m_cashsale_orgId = args.orgId;
	m_cashsale_permission = args.permission;
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
               { title: '是否提货', field: 'isdelivery', align: 'left', width: 150,
       			formatter:function(value,rowData,index){
    				if(value==2||value=="2"){
    					return "已提货";
    				}else {
    					return "未提货";
    				}
    			} },
               { title: '单据号', field: 'serialNo', align: 'left', width: 150 },
               { title: '销售部门', field: 'orgName', align: 'left', width: 150 },
               { title: '注册账号', field: 'customerName', align: 'left', width: 100 },
               { title: '真实姓名', field: 'realname', align: 'left', width: 100 },
               { title: '金额', field: 'goodsAmount', align: 'right', width: 120 },
               { title: '销售日期', field: 'saleTime', align: 'left', width: 100 },
               { title: '录入日期', field: 'recTime', align: 'left', width: 100 }
        ]]
    });
	getTotalPriceInfo();
});


function loadOrgs(){
//	var orgs=loadStockOrg();
	var orgs =  loadAllOrgById(m_cashsale_orgId);
	var a={id:0,name:'全部'};
	orgs.splice(0, 0, a );
	$("#cmbSaleDept").combobox('loadData',orgs);
	
	//$("#cmbSaleDept").combobox('select',0);
	$("#cmbSaleDept").combobox('setValue',m_cashsale_orgId);
//	if(m_cashsale_permission==0){
//		$("#cmbSaleDept").combobox('disable');
//	}
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
	m_cashsale_isdelivery = rowData.isdelivery;
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
	m_sale_query.saletype = 2;	 
	var isdeli = $("#cmbisdelivery").combobox("getValue");
	if(isdeli == ""){
		m_sale_query.isdelivery = 0;
	}else{ 
		m_sale_query.isdelivery =$("#cmbisdelivery").combobox("getValue");
	}
	var organId = $("#cmbSaleDept").combobox("getValue");
	if(organId==undefined||organId ==""||organId == 0 || organId =="0"){
		m_sale_query.orgId = m_cashsale_orgId;
	}else{
		m_sale_query.orgId = $("#cmbSaleDept").combobox("getValue");
	}
} 
function onSaleSearch(){
	loadSaleBills();
	getTotalPriceInfo();
}
function getTotalPriceInfo(){
	$.ajax({
		url : 'sale/getTotalPriceInfo.do',
		type : "POST",
		dataType : "json",
		data : {
			'saleQuery' : JSON.stringify(m_sale_query)
		},
		success : function(req) {
			if (req.isSuccess) {
				$("#totalPrice").html(fmoney(req.msg,2)+"元");
			}else{
				$.messager.alert("系统提示","获取销售总额失败","error");
				$("#totalPrice").html("0元");
			}
		}
	});
}
function fmoney(s, n) { 
	n = n > 0 && n <= 20 ? n : 2; 
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
	t = ""; 
	for (i = 0; i < l.length; i++) { 
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
	} 
	return t.split("").reverse().join("") + "." + r; 
}
function onSaleBillAdd(){
	if(m_cashsale_permission==0){
		if(!checkAuthorize2("shop_cash_sale_add")){
			return;
		}
	}
	try {
		m_sale_dlg = art.dialog({
            id: 'dlgSaleBillView',
            title: '单据',
            content: "<iframe scrolling='yes' frameborder='0' src='view/sale/cashsaleBill.jsp?optType=0&billType=2&id=0' style='width:760px;height:460px;'/>",
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
			m_sale_obj.realname = obj.realname;
		},
		
		ShowDialog:function(obj){
			try {
				m_sale_dlg = art.dialog({
		            id: 'dlgSaleBillView',
		            title: '单据',
		            content: "<iframe scrolling='yes' frameborder='0' src='view/sale/cashsaleBill.jsp?optType=1&billType=2&id="+m_sale_obj.id+"' style='width:760px;height:460px;'/>",
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




