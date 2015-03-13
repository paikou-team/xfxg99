var m_sale_query={};
var m_sale_dlg;

$(function () {
	var args = getUrlArgs();
	
    $("#cmbSaleInDept").combobox({
        valueField: 'id',
        textField: 'name',
        editable: false,
        panelHeight: 'auto',
        onSelect: function (record) { m_sale_query.stockInOrgId = record.id; }
    });
	

    $("#cmbState").combobox({
        valueField: 'value',
        textField: 'label',
        editable: false,
        panelHeight: 'auto',
        onSelect: function (record) { m_sale_query.confirmState = record.value; },
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
	setSaleQueryTime();
	setSaleConfirmState();
	loadOrgs();
    
    packQuery();
    
	$('#dgSale').datagrid({
		url:'stock/loadStockList.do',
		queryParams : {
			'saleQuery' : JSON.stringify(m_sale_query)
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


function setStockConfirmState(){
	$("#cmbState").combobox('select',0);
}

function loadOrgs(){
	var orgs=loadStockOrg();
	var a={id:0,name:'全部'};
	orgs.splice(0, 0, a );
	$("#cmbSaleInDept").combobox('loadData',orgs);
	
	$("#cmbSaleInDept").combobox('select',0);
}

function setSaleQueryTime(){
	var d=getCurrentTime();
	
	var bt=new Date(d);
	bt.add('d',-7);
	var et=new Date(d);
	
	$("#dteBeginTime").datebox('setValue',bt.toSimpleString());
	$("#dteEndTime").datebox('setValue',et.toSimpleString());
}


function onSelRow(){
	var row = $("#dgSale").datagrid("getSelected");
	
	if(row){
		m_sale_dlg = art.dialog({
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
	m_sale_query.billType=m_stock_type;
	m_sale_query.beginTime = $('#dteBeginTime').datebox('getValue');
	m_sale_query.endTime = $('#dteEndTime').datebox('getValue');
	m_sale_query.serialNo = $('#txtSerialNo').val();
}

function onSaleSearch(){
	loadSaleBills();
}

function onStaleBillAdd(){
	try {
		m_sale_dlg = art.dialog({
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

function onSaleConfirm(){
	var row = $("#dgSale").datagrid("getSelected");
	
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
					loadSaleBills();
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


function onSaleBillEdit(){
	onSelRow();
}

function onSaleDel(){
var row = $("#dgSale").datagrid("getSelected");
	
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
							loadSaleBills();
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

