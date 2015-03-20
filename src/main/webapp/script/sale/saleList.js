var m_sale_query={};
var m_sale_dlg;
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
        pageSize: 20,
        nowrap: false,
        idField: 'id',
        singleSelect: true,
        onDblClickRow: onSelRow,
        columns: [[
               { title: 'id', field: 'id', align: 'left', width: 5, hidden: true },
               { title: 'orgId', field: 'orgId', align: 'left', width: 5, hidden: true },
               { title: 'custId', field: 'custId', align: 'left', width: 5, hidden: true },
               { title: '单据号', field: 'serialNo', align: 'left', width: 150 },
               { title: '销售部门', field: 'orgName', align: 'center', width: 150 },
               { title: '客户名称', field: 'customerName', align: 'center', width: 100 },
               { title: '金额', field: 'goodsAmount', align: 'right', width: 120 },
               { title: '销售日期', field: 'saleTime', align: 'center', width: 100 },
               { title: '录入日期', field: 'recTime', align: 'center', width: 100 }
        ]]
    });
});


function loadOrgs(){
	var orgs=loadStockOrg();
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


function onSelRow(){
	var row = $("#dgSale").datagrid("getSelected");
	
	if(row){
		m_sale_dlg = art.dialog({
            id: 'dlgSaleBillView',
            title: '单据',
            content: "<iframe scrolling='yes' frameborder='0' src='view/sale/stockBill.jsp?optType=1&billType=" + m_stock_type + "&id="+row.id+"' style='width:760px;height:460px;'/>",
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
	m_sale_query.beginTime = $('#dteBeginTime').datebox('getValue');
	m_sale_query.endTime = $('#dteEndTime').datebox('getValue');
	m_sale_query.serialNo = $('#txtSerialNo').val();
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

