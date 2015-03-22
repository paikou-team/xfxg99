var m_goodsListDlg;
var m_saleDetail_rowIndex = undefined;
var m_sale_bill;
var user = getCurrentUser();
var m_selectCustomer_dlg;
var m_customer=undefined;
var m_verif_time=120*1000;

$(function() {
	var args = getUrlArgs();

	$('#dgSaleDetail').datagrid({
		fitColumns : true,
		rownumbers : true,
		resizable : true,
		pagination : false,
		nowrap : false,
		height : 180,
		idField : 'id',
		singleSelect : true,
		editRowIndex : undefined,
		// onDblClickRow: viewStockDetail,
		toolbar : "#saleDetailToolBar",
		columns : [ [ {
			title : 'id',
			field : 'id',
			align : 'left',
			width : 5,
			hidden : true
		}, {
			title : 'saleId',
			field : 'saleId',
			align : 'left',
			width : 5,
			hidden : true
		}, {
			title : 'goodsId',
			field : 'goodsId',
			align : 'left',
			width : 10,
			hidden : true
		}, {
			title : '名称',
			field : 'goodsName',
			align : 'left',
			width : 220
		}, {
			title : '数量',
			field : 'goodsNumber',
			align : 'right',
			width : 80,
			editor : {
				type : 'numberbox',
				options : {
					precision : 0
				}

			}
		}, {
			title : '商城价',
			field : 'goodsPrice',
			align : 'right',
			width : 100,
			formatter : function(value, row, index) {
				return value.toFixed(2);
			}
		}, {
			title : '金额',
			field : 'amount',
			align : 'right',
			width : 100,
			formatter : function(value, row, index) {
				return value.toFixed(2);
			}
		} ] ],

		onClickRow : function(rowIndex, row) {
			beginEdit(rowIndex, row);
		}

	});

	$("#cmbSaleDetp").combobox({
		valueField : 'id',
		textField : 'name',
		panelHeight : "auto",
		multiple : false
	// ,
	// onSelect:function (record) {
	// $("#txtorgId").val( record.id); }
	});

	var billType = args["billType"];
	var id = args["id"];

	loadOrgs();

	loadBill(billType, id);
	// setBillLockState();

	$("#btnSelectCustomer").bind("click", SelectCustUser);

	CustomerSelectManage.InitCustGrid();
	$("#btnSearchCustUser").bind("click", CustomerSelectManage.SearchCustUser);

});

function loadOrgs() {
	var orgs = loadStockOrg();
	$("#cmbSaleDetp").combobox('loadData', orgs);

	// var userOrgId = user.orgId;
	// $("#cmbSaleDetp").combobox('select',userOrgId);
}

function loadBill(billType, id) {
	if (id == 0) {
		$.ajax({
			url : 'sale/loadBill.do',
			type : "POST",
			dataType : "json",
			async : false,
			data : {
				"billType" : billType,
				'id' : id
			},
			success : function(req) {
				if (req.isSuccess) {
					m_sale_bill = req.data;
					stockBill2View(m_sale_bill);
				}
			},
		});
	}
}

// 添加商品
function onAddGoods() {
	try {
		m_goodsListDlg = art
				.dialog({
					id : 'dlgGoodsList',
					title : '商品信息',
					content : "<iframe scrolling='yes' frameborder='0' src='view/sale/goodsList.jsp?callType=2&pageSize=10' style='width:520px;height:400px;'/>",
					// content:"123",
					lock : true,
					initFn : function() {
					}
				});
	} catch (ex) {
		alert(ex);
	}
}

function onDelGoods() {
	var row = $('#dgSaleDetail').datagrid('getSelected');
	if (row) {
		var index = $('#dgSaleDetail').datagrid('getRowIndex', row);
		$('#dgSaleDetail').datagrid('deleteRow', index);

		if (index == m_saleDetail_rowIndex) {
			m_saleDetail_rowIndex = undefined;
		}
	}
}

function onSelGoods(goods) {
	if (goods) {
		var row = {};
		row.id = 0;
		row.goodsId = goods.id;
		row.goodsName = goods.name;
		row.goodsNumber = 1;
		row.goodsPrice = goods.shopPrice;
		row.amount = row.goodsNumber * goods.shopPrice;
		$('#dgSaleDetail').datagrid('appendRow', row);
		var data = $('#dgSaleDetail').datagrid('getData');
		var index = data.total - 1;
		$('#dgSaleDetail').datagrid('selectRow', index);
		beginEdit(index);
	}
}

function beginEdit(rowIndex) {

	if (rowIndex != m_saleDetail_rowIndex) {

		if (m_saleDetail_rowIndex != undefined) {
			endEdit(m_saleDetail_rowIndex);
		}

		$('#dgSaleDetail').datagrid('beginEdit', rowIndex);

		var ed = $('#dgSaleDetail').datagrid('getEditor', {
			index : rowIndex,
			field : 'goodsNumber'
		});
		$(ed.target).focus();
	}
	m_saleDetail_rowIndex = rowIndex;
}

function endEdit(rowIndex) {
	var ed = $('#dgSaleDetail').datagrid('getEditor', {
		index : rowIndex,
		field : 'goodsNumber'
	});

	if (ed != null) {
		var data = $('#dgSaleDetail').datagrid('getData');
		var row = data.rows[rowIndex];
		var number = $(ed.target).numberbox('getValue');
		row.number = number;
		row.amount = row.goodsPrice * row.number;
		$('#dgSaleDetail').datagrid('endEdit', rowIndex);
		$('#dgSaleDetail').datagrid('refreshRow', rowIndex);
	}
	m_saleDetail_rowIndex = undefined;
}

function calcAmount(rowIndex, row) {
	row.amount = row.goodsPrice * row.number;
}

function setBillLockState() {
	if (m_sale_bill.confirmerId > 0) {
		$('#cmbSaleDetp').combobox("disable");
		$("#dteSaleTime").datetimebox('disable');
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
function onCheckStockBill() {

	if (m_sale_bill.id == undefined || m_stokc_bill.id == null
			|| m_sale_bill.id == 0) {
		$.messager.alert("提示", "请先保存单据!", "info");
	} else {
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
					m_sale_bill = req.data;
					$("#txtConfirmerOrgName").val(m_sale_bill.serialNo);
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
/**
 * 获取短信验证码
 */
function onGetVerifCode(){

	if(!checkStockBill()){
		return ;
	}
	$.ajax({
		url : "sale/sendVerifCode.do",
		type : "POST",
		dataType : "json",
		async : false,
		data : {'mobile' : m_customer.mobile,'custId':m_customer.id},
		success : function(req) {
			if (req.isSuccess) {
				verifCodeDelay();
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

function verifCodeDelay(){
	var cts=m_verif_time;
	$('#btnGetVerifCode').linkbutton({disabled:true});
	var tt=setInterval(function(){
		$('#btnGetVerifCode').text("还有"+cts/1000+"秒");
		cts-=1000;
		if(cts<=0){
			clearInterval(tt);
			$('#btnGetVerifCode').linkbutton({disabled:false});
			$('#btnGetVerifCode').val("获取验证码");
		}
	},1000);
}


function stockBill2View(bill) {
	$('#txtSerialNo').val(bill.serialNo);

	if (bill.orgId) {
		$("#cmbSaleDetp").combobox("setValue", bill.orgId);
	}

	if (bill.saleTime) {
		$("#dteSaleTime").datetimebox("setValue", bill.saleTime);
	}

	$("#txtPreparerOrgName").val(bill.preparerOrgName);
	$("#txtPreparerName").val(bill.preparerName);
	$("#txtPrepareTime").val(bill.recTime);
	//	
	// $("#txtConfirmerOrgName").val(bill.confirmerOrgName);
	// $("#txtConfirmerName").val(bill.confirmerName);
	// $("#txtConfirmTime").val(bill.confirmTime);

	if (bill.stockGoods != undefined && bill.stockGoods != null) {
		for ( var i = 0; i++; i < bill.stockGoods) {
			var row = bill.stockGoods[i];
			row.amount = row.goodsPrice * row.number;
		}

		$('#dgSaleDetail').datagrid('loadData', bill.stockGoods);
	}

}

function view2stockBill() {
	if (m_saleDetail_rowIndex != undefined) {
		endEdit(m_saleDetail_rowIndex);
	}
	m_sale_bill.billTime = $('#dteSaleTime').datetimebox('getValue');
	m_sale_bill.description = $('#txtDescription').val();
	
	m_sale_bill.custId = $("#txtcustId").val();
	m_sale_bill.customerName = $("#textSaleCustomer").val();
}
/**
 * 保存前检查
 * 
 * @returns {Boolean}
 */
function checkStockBill() {

	if(m_customer==undefined ){
		$.messager.alert("提示", "请选择客户!", "info");
		return false;
	}
	
	if(m_customer.phone ==undefined || m_customer.phone==""){
		$.messager.alert("提示", "客户必须绑定手机号码!", "info");
		return false;
	}
	
	if (m_sale_bill.billType == 10 && !m_sale_bill.orgId > 0) {
		$.messager.alert("提示", "请选择销售部门!", "error");
		return false;
	}

	var data = $('#dgSaleDetail').datagrid('getData');

	if (data.total == 0) {
		$.messager.alert("提示", "请选择商品!", "error");
		return false;
	}

	for ( var i = 0; i++; i < data.total) {
		var r = data.rows[i];

		if (r.goodsNumber == 0) {
			$.messager.alert("提示", "商品数量不能等于0!", "error");
			return false;
		}
	}

	return true;

}

function onSaveSaleBill() {

	view2stockBill();

	if (!checkStockBill()) {
		return;
	}

	if($('#txtVerifCode').val()==""){
		$.messager.alert("提示信息", "请输入验证码", "info");
		return;
	}
	
	var data = $('#dgSaleDetail').datagrid('getData'); 
	m_sale_bill.stockGoods = data.rows;

	$.ajax({
		url : "sale/saveSaleBill.do",
		type : "POST",
		dataType : "json",
		async : false,
		data : {
			'bill' : JSON.stringify(m_sale_bill)
		},
		success : function(req) {
			if (req.isSuccess) {
				m_sale_bill = req.data;
				// $("#policeInfoinportwindow").window("close");
				$("#txtSerialNo").val(m_sale_bill.serialNo);
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
function onExit() {
	parent.art.dialog.list['dlgSaleBillView'].close();
}
function SelectCustUser() {
	m_selectCustomer_dlg = art.dialog({
		id : 'dlgChargeUser',
		title : '客户选择',
		content : document.getElementById("div_custuser"),
		lock : false,
		width : 500,
		height : 300,
		initFn : function() {
			$('#custUserGrid').datagrid("reload");
		}
	});

}
var CustomerSelectManage = {
	InitCustGrid : function() {
		$('#custUserGrid').datagrid({
			url : 'charge/getcustList.do',
			fitColumns : true,
			rownumbers : true,
			resizable : true,
			pagination : true,
			pageNumber : 1,
			pageSize : 10,
			nowrap : false,
			idField : 'id',
			height : '99%',
			width : '99%',
			singleSelect : true,
			onDblClickRow : CustomerSelectManage.SelectCustUserAction,
			toolbar : "#tb_custUser",
			columns : [ [ {
				title : 'id',
				field : 'id',
				align : 'left',
				width : 5,
				hidden : true
			}, {
				title : '客户姓名',
				field : 'name',
				align : 'center',
				width : 150
			}, {
				title : '联系方式',
				field : 'phone',
				width : 150,
				align : 'center'
			}, {
				title : '电子邮件',
				field : 'email',
				align : 'left',
				width : 250
			} ] ]
		});
	},

	SearchCustUser : function() {
		$('#custUserGrid').datagrid("reload", {
			"name" : $("#search_custname").val()
		});
	},
	SelectCustUserAction : function(index, rowData) {
		
		m_customer={};
		m_customer.id=rowData.id;
		m_customer.name=rowData.name;
		m_customer.phone = rowData.phone;
		
		$("#txtcustId").val(m_customer.id);
		$("#textSaleCustomer").val(m_customer.name);
		$("#txtMobile").val(m_customer.phone);
		m_selectCustomer_dlg.close();
	}
};