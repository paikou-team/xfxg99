var m_stock_query={};
var m_stock_dlg;
var m_stock_type;

$(function () {
	var args = getUrlArgs();
	m_stock_type =parseInt( args["billType"]);
	
    $("#cmbStockInDept").combobox({
        valueField: 'id',
        textField: 'name',
        editable: false,
        panelHeight: 'auto',
        onSelect: selectInDept 
    });
	
    $("#cmbStockOutDept").combobox({
        valueField: 'id',
        textField: 'name',
        editable: false,
        panelHeight: 'auto',
        onSelect: selectOutDept
    });
	
    $("#cmbState").combobox({
        valueField: 'value',
        textField: 'label',
        editable: false,
        panelHeight: 'auto',
        onSelect: selectState,
        data:[{
        	label:'全部',
        	value:0
        },
        {
        	label:'已确认',
        	value:1
        },
        {
        	label:'未确认',
        	value:2
        }]
    });
    setOrgEnable();
	setStockQueryTime();
	setStockConfirmState();
	loadOrgs();
    
    packQuery();
    
	$('#dgStock').datagrid({
		url:'stock/loadStockList.do',
		queryParams : {
			'stockQuery' : JSON.stringify(m_stock_query)
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
               { title: '单据编号', field: 'serialNo', align: 'left', width: 150 },
               { title: '入库部门', field: 'stockInOrgName', align: 'left', width: 120 },
               { title: '出库部门', field: 'stockOutOrgName', align: 'left', width: 120 },
               { title: '单据日期', field: 'billTime', align: 'center', width: 100 },
               { title: '确认', field: 'confirmerId', align: 'left', width: 100 ,
            	   formatter:function(value,row,index){
            		   if(value > 0){
            			   return "已确认";
            		   }else{
            			   return "未确认";
            		   }
            	 }}
        ]]
    });
});

function setOrgEnable(){
	switch(m_stock_type){
	case 10:
		$("#liStockOutOrgId").hide();
		break;
	case 11:
		$("#liStockInOrgId").hide();
		break;
	}
}
function selectInDept(record) {
	m_stock_query.stockInOrgId = record.id; 
}
function  selectOutDept (record) { 
	m_stock_query.stockOutOrgId = record.id; 
}
function selectState (record) { 
	m_stock_query.confirmState = record.value; 
}
function setStockConfirmState(){
	$("#cmbState").combobox('select',0);
}

function loadOrgs(){
	var orgs=loadStockOrg();
	var a={id:0,name:'全部'};
	orgs.splice(0, 0, a );
	$("#cmbStockInDept").combobox('loadData',orgs);
	$("#cmbStockOutDept").combobox('loadData',orgs);
	
	$("#cmbStockInDept").combobox('select',0);
	$("#cmbStockOutDept").combobox('select',0);
}

function setStockQueryTime(){
	var d=getCurrentTime();
	
	var bt=new Date(d);
	bt.add('d',-7);
	var et=new Date(d);
	
	$("#dteBeginTime").datebox('setValue',bt.toSimpleString());
	$("#dteEndTime").datebox('setValue',et.toSimpleString());
}


function onSelRow(){
	var row = $("#dgStock").datagrid("getSelected");
	
	if(row){
		m_stock_dlg = art.dialog({
            id: 'dlgStockBillView',
            title: '单据',
            content: "<iframe scrolling='yes' frameborder='0' src='view/sale/stockBill.jsp?billType=" + m_stock_type + "&id="+row.id+"' style='width:760px;height:460px;'/>",
            //content:"123",
            lock: true,
            initFn: function () {
            }
        });
	}
}

function loadStockBills() {

    try {
        packQuery();
        var json = JSON.stringify(m_stock_query);
        $('#dgStock').datagrid('reload', { 'stockQuery': json });

    } catch (ex) {
        alert(ex);
    }
}

function packQuery(){
	m_stock_query.billType=m_stock_type;
	m_stock_query.beginTime = $('#dteBeginTime').datebox('getValue');
	m_stock_query.endTime = $('#dteEndTime').datebox('getValue');
	m_stock_query.serialNo = $('#txtSerialNo').val();
}

function onStockSearch(){
	loadStockBills();
}

function onStockBillAdd(){
	
	if(m_stock_type==10 && !checkAuthorize2("shop_stockin_add")){
		return;
	}else if(m_stock_type==11 && !checkAuthorize2("shop_stockout_add")){
		return;
	}else if(m_stock_type==12 && !checkAuthorize2("shop_transfer_add")){
		return;
	}
	
	try {
		m_stock_dlg = art.dialog({
            id: 'dlgStockBillView',
            title: '单据',
            content: "<iframe scrolling='yes' frameborder='0' src='view/sale/stockBill.jsp?billType=" + m_stock_type + "&id=0' style='width:760px;height:460px;'/>",
            //content:"123",
            lock: true,
            initFn: function () {
            }
        });
    } catch (ex) {
        alert(ex);
    }
}

function onStockConfirm(){
	
	if(m_stock_type==10 && !checkAuthorize2("shop_stockin_confirm")){
		return;
	}else if(m_stock_type==11 && !checkAuthorize2("shop_stockout_confirm")){
		return;
	}else if(m_stock_type==12 && !checkAuthorize2("shop_transfer_confirm")){
		return;
	}
	
	var row = $("#dgStock").datagrid("getSelected");
	
	if(row){
		$.ajax({
			url : "stock/confirmBill.do",
			type : "POST",
			dataType : "json",
			async : false,
			data : {
				'id' : row.id
			},
			success : function(req) {
				gRequestData(req);
				
				if(req.isSuccess){
					loadStockBills();
				}
			},
			failer : function(a, b) {
				$.messager.alert("消息提示", "保存失败", "error");
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert("错误提示", "保存失败", "error");
			}
		});
	}else{
		$.messager.alert("提示", "请选择单据!", "info");
	}
	
}


function onStockBillEdit(){
	onSelRow();
}

function onStockDel(){
	
	if(m_stock_type==10 && !checkAuthorize2("shop_stockin_del")){
		return;
	}else if(m_stock_type==11 && !checkAuthorize2("shop_stockout_del")){
		return;
	}else if(m_stock_type==12 && !checkAuthorize2("shop_transfer_del")){
		return;
	}
	
	var row = $("#dgStock").datagrid("getSelected");
	
	if(row){
		
		$.messager.confirm('操作提示', "确定删除编号为： " + row.serialNo + " 的单据?", function (r) {
			if(r){
				$.ajax({
					url : "stock/delStockBill.do",
					type : "POST",
					dataType : "json",
					async : false,
					data : {
						'id' : row.id
					},
					success : function(req) {
						gRequestData(req);
						
						if(req.isSuccess){
							$.messager.alert("消息提示", "单据删除成功!", "info");
							loadStockBills();
						}
						
					},
					failer : function(a, b) {
						$.messager.alert("消息提示", "保存失败", "error");
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						$.messager.alert("错误提示", "保存失败", "error");
					}
				});
			}
		});
		
		
	}else{
		$.messager.alert("提示", "请选择单据!", "info");
	}
}

