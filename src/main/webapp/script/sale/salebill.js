var m_goodsListDlg;
var m_saleDetail_rowIndex = undefined;
var m_sale_bill;
var user = getCurrentUser();
var m_selectCustomer_dlg;
var m_customer=undefined;
var m_verif_time=120*1000;
var m_time_interval;
var m_optType = 0;
$(function() {
	var args = getUrlArgs(); 
	if (args.optType == 1 || args.optType == "1") {
		m_optType = 1;
		$("#tb_operationtb").hide();
		$("#viewBillInfo").hide();
		$("#txtVerifCode").hide();
		$("#btnGetVerifCode").hide();
		$("#labelCode").hide();
		$("#dteSaleTime").datebox('disable');
		$('#txtDescription').attr("disabled", true);
		$("#textSaleCustomer").attr("disabled",true);
		$("#txtMobile").attr("disabled",true);
		$("#txtrealname").attr("disabled",true);
		
//		fillInBlankInfo();
	}
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
//	var orgs = loadStockOrg();
	var orgs = loadAllOrg();
	$("#cmbSaleDetp").combobox('loadData', orgs);

	// var userOrgId = user.orgId;
	// $("#cmbSaleDetp").combobox('select',userOrgId);
}
function fillInBlankInfo() {
	$("#textSaleCustomer").val(parent.m_sale_obj.customerName);
	$.ajax({
		url : 'sale/loadProductListByBillId.do',
		type : "POST",
		dataType : "json",
		data : {
			'id' : parent.m_sale_obj.id
		},
		success : function(req) {
			if (req.isSuccess) {
				if(req.rows.length>0){
					for(var i =0; i<req.rows.length;i++){
						var row = {};
						row.id = 0;
						row.goodsId = 0;
						row.goodsName = req.rows[i].goodsName;
						row.goodsNumber = req.rows[i].goodsNumber;
						row.goodsPrice = req.rows[i].goodsPrice;
						row.amount = req.rows[i].goodsNumber * req.rows[i].goodsPrice;
						$('#dgSaleDetail').datagrid('appendRow', row);
					}
				}
				
			} else {
				$.messager.alert("系统提示", req.msg, "info");
			}
		},
	});
}
function loadBill(billType, id) {
//	if (id == 0) {
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
//	}
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

	if(m_optType == 1 || m_optType == "1"){
		return;
	}
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


/**
 * 获取短信验证码
 */
function onGetVerifCode(){

	view2stockBill();
	
	if(!checkStockBill()){
		return ;
	}
	
	var amount=calcTotal();
	$.ajax({
		url : "sale/sendVerifCode.do",
		type : "POST",
		dataType : "json",
		async : false,
		data : {'mobile' : m_customer.phone,'custId':m_customer.id,'amount':amount},
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

function calcTotal(){
	var data = $('#dgSaleDetail').datagrid('getData');
	var rows=data.rows;
	var total=data.total;
	var amount=0;
	var i=0;
	
	for(i=0;i<total;i++){
		amount+=rows[i].amount;
	}
	return amount;
}

function verifCodeDelay(){
	var cts=m_verif_time;
	$('#btnGetVerifCode').linkbutton({disabled:true});
	$('#btnGetVerifCode').text("还有"+cts/1000+"秒");
	cts-=1000;
	m_time_interval=setInterval(function(){
		$('#btnGetVerifCode').text("还有"+cts/1000+"秒");
		cts-=1000;
		if(cts<=0){
			clearTimeInterval();
		}
	},1000);
}


function clearTimeInterval(){
	clearInterval(m_time_interval);
	$('#btnGetVerifCode').linkbutton({disabled:false});
	$('#btnGetVerifCode').val("获取验证码");
}

function stockBill2View(bill) {
	$('#txtSerialNo').val(bill.serialNo);

	if (bill.orgId) {
		$("#cmbSaleDetp").combobox("setValue", bill.orgId);
	}

	if (bill.saleTime) {
		$("#dteSaleTime").datebox("setValue", bill.saleTime);
	}
	
	if(bill.customerPhone){
		$("#txtMobile").val(bill.customerPhone);
	}
	if(bill.customerName){
		$("#textSaleCustomer").val(bill.customerName);
	}

	$("#txtPreparerOrgName").val(bill.preparerOrgName);
	$("#txtPreparerName").val(bill.preparerName);
	$("#txtPrepareTime").val(bill.recTime);
	$('#txtDescription').val(bill.description);
	$("#txtrealname").val(parent.m_sale_obj.realname);

	if (bill.saleGoods != undefined && bill.saleGoods != null) {
		for ( var i = 0; i < bill.saleGoods.length;i++) {
			var row = bill.saleGoods[i];
			row.amount = row.goodsPrice * row.goodsNumber;
		}

		$('#dgSaleDetail').datagrid('loadData', bill.saleGoods);
	}

}

function view2stockBill() {
	if (m_saleDetail_rowIndex != undefined) {
		endEdit(m_saleDetail_rowIndex);
	}
	m_sale_bill.billTime = $('#dteSaleTime').datebox('getValue');
	m_sale_bill.description = $('#txtDescription').val();

	m_sale_bill.custId = $("#txtcustId").val();
	m_sale_bill.customerPhone = $("#txtMobile").val();
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
  
	var data = $('#dgSaleDetail').datagrid('getData'); 
//	if($('#txtVerifCode').val()==""){
//		$.messager.alert("提示信息", "请输入验证码", "info");
//		return;
//	} 
	if($('#txtVerifCode').val()==""){
		$.messager.alert("提示信息", "请输入验证码", "info");
		return;
	}
	
	clearTimeInterval(); 
	
	//var data = $('#dgSaleDetail').datagrid('getData');  
	m_sale_bill.saleGoods = data.rows;

	$.ajax({
		url : "sale/saveSaleBill.do",
		type : "POST",
		dataType : "json",
		async : false,
		data : {
			'bill' : JSON.stringify(m_sale_bill),
			"verifCode": $('#txtVerifCode').val()
		},
		success : function(req) {
			if (req.isSuccess) {
				m_sale_bill = req.data;
				// $("#policeInfoinportwindow").window("close");
				$("#txtSerialNo").val(m_sale_bill.serialNo);
				printSaleBill();
				parent.onSaleSearch();
				parent.m_sale_dlg.close();
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
			url : 'charge/getcustList.do?typeId=1',
			fitColumns : true,
			rownumbers : true,
			resizable : true,
			pagination : true,
			pageNumber : 1,
			pageSize : 10, 
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
				title : '注册账号',
				field : 'name',
				align : 'center',
				width : 150
			}, {
				title : '真实姓名',
				field : 'realname',
				width : 150,
				align : 'center'
			}, {
				title : '联系方式',
				field : 'phone',
				width : 150,
				align : 'center'
			}, {
				title : '电子邮件',
				field : 'email',
				align : 'left',
				width : 250,
				hidden: true
			} ] ]
		});
	},

	SearchCustUser : function() {
		$('#custUserGrid').datagrid("reload", {
			"name" : $("#search_custname").val(),
			"realname" : $("#search_realname").val(),
			"phone": $("#search_phone").val()
		});
	},
	SelectCustUserAction : function(index, rowData) {
		
		m_customer={};
		m_customer.id=rowData.id;
		m_customer.name=rowData.name;
		m_customer.phone = rowData.phone;
		m_customer.realname = rowData.realname;
		$("#txtcustId").val(m_customer.id);
		$("#textSaleCustomer").val(m_customer.name);
		$("#lbl_customerName").html(m_customer.realname);
		$("#txtMobile").val(m_customer.phone);
		$("#txtrealname").val(m_customer.realname);
		m_selectCustomer_dlg.close();
	}
};

function printSaleBill(){
	try{
		var myDate = new Date();
		var orgname = $("#cmbSaleDetp").combobox("getText");
		var serialNo = $("#txtSerialNo").val();
		$("#lbl_orgName").html(orgname+"欢迎您！");
		$("#lbl_saleSerialNo").html(serialNo);
		$("#lbl_saleTime").html(myDate.getFullYear() + "-" + (myDate.getMonth() + 1) + "-"
				+ myDate.getDate() + " " + myDate.getHours() + ":"
				+ myDate.getMinutes());
		var data = $('#dgSaleDetail').datagrid('getData');
		var goodsList = data.rows;
		if(goodsList.length>0){
			var productHtml="";
			var totalCount = 0;
			var totalAmout = 0;
			for(var i = 0; i<goodsList.length;i++){
				var obj = goodsList[i];
				var goodsName = obj.goodsName;
//				if(goodsName.length>7){
//					goodsName = goodsName.substring(0,7)+"…";
//				}
				productHtml+="<tr><td style='text-align:left;width:95px'><label>"+goodsName+"</label></td><td style='text-align:center;width:30px'><label>"+obj.goodsNumber+"</label></td><td style='text-align:center;'><label>"+fmoney(obj.goodsPrice,2)+"</label></td></tr>"
				totalCount +=Number(obj.goodsNumber);
				totalAmout +=obj.amount;
			}
			totalAmout = fmoney(totalAmout,2);
			$("#tbl_productList").html(productHtml);
			$("#lbl_totalcount").html(totalCount);
			$("#lbl_totalAmount").html(totalAmout);
			var bdhtml=document.getElementById("div_printSaleBill").innerHTML;    
	        var sprnstr="<!--startprint-->";    
	        var eprnstr="<!--endprint-->";    
	        var prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+17);    
	        prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));     
	        window.document.body.innerHTML=prnhtml;
	        window.print();      
		}

	}
	catch(ex){
		$.messager.alert("系统提示","打印小票出错，请查看是否设置为默认打印机或者采用其他兼容浏览器","error");
	}

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