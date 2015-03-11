
var	m_goodsListDlg;
var m_stockDetail_rowIndex=undefined;
var m_stock_bill;

$(function () {
	var args = getUrlArgs();
	
	$('#dgStockDetail').datagrid({
        fitColumns: true,
        rownumbers: true,
        resizable: true,
        pagination: false,
        nowrap: false,
        height: 180,
        idField: 'id',
        singleSelect: true,
        editRowIndex :undefined,
        //onDblClickRow: viewStockDetail,
        toolbar: "#stockDetailToolBar",
        columns: [[
               { title: 'id', field: 'id', align: 'left', width: 5, hidden: true },
               { title: 'stockId', field: 'stockId', align: 'left', width: 5, hidden: true },
               { title: 'goodsId', field: 'goodsId', align: 'left', width: 10,hidden: true},
               { title: '名称', field: 'goodsName', align: 'left', width: 220  },
               { title: '数量', field: 'goodsNumber', align: 'right', width: 80,
            	   editor:{
            		   type:'numberbox',
            		   options:{precision:0}
            		   
            	   }},
               { title: '商城价', field: 'goodsPrice', align: 'right', width: 100,
                	   formatter:function(value,row,index){
                		   return value.toFixed(2);
                	   }},
               { title: '金额', field: 'amount', align: 'right', width: 100 ,
            	   formatter:function(value,row,index){
            		   return value.toFixed(2);
            	   }}
        ]],
        
        onClickRow:function(rowIndex,row){
        	beginEdit(rowIndex,row);
        }

    });
	
	$("#cmbStockInDetp").combobox({
		valueField : 'id',
		textField : 'name',
		panelHeight : "auto",
		multiple : false,
		onSelect:function (record) { m_stock_bill.stockInOrgId = record.id; }
	});
	
	$("#cmbStockOutDetp").combobox({
		valueField : 'id',
		textField : 'name',
		panelHeight : "auto",
		multiple : false,
		onSelect:function (record) { m_stock_bill.stockOutOrgId = record.id; }
	});
	
	var billType=args["billType"];
	var id=args["id"];
	
	loadOrgs();
	
	loadBill(billType,id);
	setBillLockState();
});

function loadOrgs(){
	var orgs=loadStockOrg();
	$("#cmbStockInDetp").combobox('loadData',orgs);
	$("#cmbStockOutDetp").combobox('loadData',orgs);
	
}

function loadBill(billType,id){
	if(id==0){
		$.ajax({
	        url: 'stock/loadBill.do',
	        type: "POST",
	        dataType: "json",
	        async: false,
	        data: { "billType": billType,'id':id },
	        success: function (req) {
	            if (req.isSuccess) {
	            	m_stock_bill=req.data;
	            	stockBill2View(m_stock_bill);
	            }
	        },
	    });
	}
}

//添加商品
function onAddGoods(){
	try {
		m_goodsListDlg = art.dialog({
            id: 'dlgGoodsList',
            title: '商品信息',
            content: "<iframe scrolling='yes' frameborder='0' src='view/sale/goodsList.jsp?callType=2&pageSize=10' style='width:520px;height:400px;'/>",
            //content:"123",
            lock: true,
            initFn: function () {
            }
        });
    } catch (ex) {
        alert(ex);
    }
}

function onDelGoods(){
	var row = $('#dgStockDetail').datagrid('getSelected');
    if (row) {
    	var index=$('#dgStockDetail').datagrid('getRowIndex', row);
    	$('#dgStockDetail').datagrid('deleteRow', index);
    	
    	if(index ==m_stockDetail_rowIndex ){
    		m_stockDetail_rowIndex=undefined;
    	}
    }
}


function onSelGoods(goods){
	if(goods){
		var row={};
		row.id=0;
		row.goodsId=goods.id;
		row.goodsName=goods.name;
		row.goodsNumber=1;
		row.goodsPrice=goods.shopPrice;
		row.amount=row.goodsNumber * goods.shopPrice;
		$('#dgStockDetail').datagrid('appendRow',row);
		var data=$('#dgStockDetail').datagrid('getData');
		var index=data.total-1;
		$('#dgStockDetail').datagrid('selectRow',index);
		beginEdit(index);
	}
}

function beginEdit(rowIndex){
	
	if(rowIndex != m_stockDetail_rowIndex){
		
		if(m_stockDetail_rowIndex != undefined){
			endEdit(m_stockDetail_rowIndex);
		}
		
		$('#dgStockDetail').datagrid('beginEdit', rowIndex);
		
		var ed = $('#dgStockDetail').datagrid('getEditor', {index: rowIndex, field: 'goodsNumber' });
		$(ed.target).focus();
	}
	m_stockDetail_rowIndex = rowIndex;
}

function endEdit(rowIndex){
	var ed = $('#dgStockDetail').datagrid('getEditor', { index: rowIndex, field: 'goodsNumber' });
	
	if(ed != null){
		var data=$('#dgStockDetail').datagrid('getData');
		var row=data.rows[rowIndex];
	    var number = $(ed.target).numberbox('getValue');
	    row.number=number;
	    row.amount=row.goodsPrice * row.number;
		$('#dgStockDetail').datagrid('endEdit', rowIndex);
		$('#dgStockDetail').datagrid('refreshRow', rowIndex);
	}
	m_stockDetail_rowIndex=undefined;
}

function calcAmount(rowIndex,row){
	row.amount=row.goodsPrice * row.number;
}


function setBillLockState(){
	if(m_stock_bill.confirmerId >0){
		$('#cmbStockInDetp').combobox("disable");
		$('#cmbStockOutDetp').combobox("disable");
		$("#dteStockTime").datetimebox('disable');
		$('#txtDescription').attr("disabled", true);
		
		$('#btnAddGoods').hide();
        $('#btnDelGoods').hide();
        $('#btnSaveBill').hide();
        $('#btnCheckBill').hide();
	}
}
/*
 * 确认入库
 */
function onCheckStockBill(){
	
	if(m_stock_bill.id ==undefined || m_stokc_bill.id ==null || m_stock_bill.id==0){
		$.messager.alert("提示", "请先保存单据!", "info");
	}else{
		$.ajax({
			url : "stock/confirmBill.do",
			type : "POST",
			dataType : "json",
			async : false,
			data : {
				'id' : m_stokc_bill.id
			},
			success : function(req) {
				if (req.isSuccess) {
					m_stock_bill=req.data;
					$("#txtConfirmerOrgName").val(m_stock_bill.serialNo);
				} else {
					$.messager.alert("提示信息", req.msg, "info");
				}
			},
			failer : function(a, b) {
				$.messager.alert("消息提示", "保存失败", "info");
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert("错误提示", "保存失败", "error");
			}
		});
	}
	

	
	setBillLockState();
}

function stockBill2View(bill){
	$('#txtSerialNo').val(bill.serialNo);
	
	if (bill.stockInOrgId) {
        $("#cmbStockInDetp").combobox("select", bill.stockInOrgId);
    }
	
	if (bill.stockOutOrgId) {
        $("#cmbStockOutDetp").combobox("select", bill.stockOutOrgId);
    }
	
	if (bill.billTime) {
		//var d=gCreateDate(bill.billTime);
        $("#dteStockTime").datetimebox("setValue", bill.billTime);
    }
	
	$("#txtPreparerOrgName").val(bill.preparerOrgName);
	$("#txtPreparerName").val(bill.preparerName);
	$("#txtPrepareTime").val(bill.prepareTime);
	
	$("#txtConfirmerOrgName").val(bill.confirmerOrgName);
	$("#txtConfirmerName").val(bill.confirmerName);
	$("#txtConfirmTime").val(bill.confirmTime);
	
	if(bill.stockGoods !=undefined && bill.stockGoods !=null){
		for(var i=0;i++;i<bill.stockGoods){
			var row=bill.stockGoods[i];
			row.amount=row.goodsPrice * row.number;
		}
		
		$('#dgStockDetail').datagrid('loadData',bill.stockGoods);
	}
	
	
}


function view2stockBill(){
	if(m_stockDetail_rowIndex !=undefined){
		endEdit(m_stockDetail_rowIndex);
	}
	m_stock_bill.billTime=$('#dteStockTime').datetimebox('getValue');
	m_stock_bill.description=$('#txtDescription').val();
}
/**
 * 保存前检查
 * @returns {Boolean}
 */
function  checkStockBill(){

	if(m_stock_bill.billType==10 && !m_stock_bill.stockInOrgId>0){
		$.messager.alert("提示", "请选择入库部门!", "error");
		return false;
	}
	
	if(m_stock_bill.billType==11 && !m_stock_bill.stockOutOrgId>0 ){
		$.messager.alert("提示", "请选择出库部门!", "error");
		return false;
	}
	
	if(m_stock_bill.billType==12 && !m_stock_bill.stockOutOrgId>0 && !m_stock_bill.stockInOrgId>0 ){
		$.messager.alert("提示", "请选择出/入库部门!", "error");
		return false;
	}
	
	var data=$('#dgStockDetail').datagrid('getData');
	
	if(data.total==0){
		$.messager.alert("提示", "请选择商品!", "error");
		return false;
	}
		
	for(var i=0;i++;i<data.total){
		var r=data.rows[i];
		
		if(r.goodsNumber==0){
			$.messager.alert("提示", "商品数量不能等于0!", "error");
			return false;
		}
	}

	return true;
	
}

function onSaveStockBill(){
	
	view2stockBill();
		
	if(!checkStockBill()){
		return;
	}
	
	var data=$('#dgStockDetail').datagrid('getData');
	
	m_stock_bill.stockGoods=data.rows;
		
	$.ajax({
		url : "stock/saveStockBill.do",
		type : "POST",
		dataType : "json",
		async : false,
		data : {
			'bill' : JSON.stringify(m_stock_bill)
		},
		success : function(req) {
			if (req.isSuccess) {
				m_stock_bill=req.data;
				//$("#policeInfoinportwindow").window("close");
				$("#txtSerialNo").val(m_stock_bill.serialNo);
			} else {
				$.messager.alert("提示信息", req.msg, "info");
			}
		},
		failer : function(a, b) {
			$.messager.alert("消息提示", "保存失败", "info");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.messager.alert("错误提示", "保存失败", "error");
		}
	});
	
}
