
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
               { title: 'goodsId', field: 'goodsId', align: 'left', width: 10,hidden: true},
               { title: '名称', field: 'goodsName', align: 'left', width: 220  },
               { title: '数量', field: 'number', align: 'right', width: 80,
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
	var billType=args["billType"];
	var id=args["id"];
	
	loadBill(billType,id);
	
});


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
    }
}


function onSelGoods(goods){
	if(goods){
		var row={};
		row.id=0;
		row.goodsId=goods.id;
		row.goodsName=goods.name;
		row.number=1;
		row.goodsPrice=goods.shopPrice;
		row.amount=row.number * goods.shopPrice;
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
		
		var ed = $('#dgStockDetail').datagrid('getEditor', {index: rowIndex, field: 'number' });
		$(ed.target).focus();
	}
	m_stockDetail_rowIndex = rowIndex;
}

function endEdit(rowIndex){
	var ed = $('#dgStockDetail').datagrid('getEditor', { index: rowIndex, field: 'number' });
	var data=$('#dgStockDetail').datagrid('getData');
	var row=data.rows[rowIndex];
    var number = $(ed.target).numberbox('getValue');
    row.number=number;
    row.amount=row.goodsPrice * row.number;
	$('#dgStockDetail').datagrid('endEdit', rowIndex);
	$('#dgStockDetail').datagrid('refreshRow', rowIndex);
	m_stockDetail_rowIndex=undefined;
}

function calcAmount(rowIndex,row){
	row.amount=row.goodsPrice * row.number;
}

function onCheckBill(){
	var row={id:0,goodsId:0,goodsName:'123',number:1,goodsPrice:10.0,allocationPrice:10.0,amount:10};
	$('#dgStockDetail').datagrid('appendRow',row);
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
	
}
