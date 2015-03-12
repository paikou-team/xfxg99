var chargeObj = {};
var opType;
var m_chargeuser_dlg;
$(function() {

	var args = getUrlArgs();
	var myDate = new Date();
	var typeid = args.type;
	if (typeid == 0 || typeid == "0") {
		opType = "add";
		getCurrentUserInfo();

		$("#txtcustName").val("");
		$("#txtmoney").numberbox('setValue', 0.00);
		$("#txtrechargeTime").datetimebox(
				'setValue',
				myDate.getFullYear() + "-" + (myDate.getMonth() + 1) + "-"
						+ myDate.getDate() + " " + myDate.getHours() + ":"
						+ myDate.getMinutes());
		$("#txtrechargeDesc").val("");

	} else {
		opType = "edit";
		$("#tb_operation").hide();
		$("#btnSelectCustUser").hide();
		var score = parent.m_charge_object;
		$("#txtorgName").val(score.orgName);
		$("#txtcustName").val(score.custName);
		$("#txtcustEmail").val(score.custEmail);
		$("#txtcustPhone").val(score.custPhone);
		$("#txtmoney").numberbox('setValue', score.money);
		$("#txtrechargeTime").datetimebox('setValue', score.rechargeTime);
		$("#txtrechargeDesc").val(score.rechargeDesc);
	}

	chargeObj.id = 0;

	$("#btnSaveChargeInfo").bind("click", ChargeBillManage.SaveChargeAction);
	$("#btnCancelSave").bind("click", ChargeBillManage.CancelSaveAction);
	$("#btnSelectCustUser").bind("click", ChargeBillManage.SelectCustUser);
	$("#btnSearchCustUser").bind("click", ChargeBillManage.SearchCustUser);

	ChargeBillManage.InitCustGrid();
});

var ChargeBillManage = {
	SaveChargeAction : function() {
		var orgid = $("#txtorgId").val();
		chargeObj.orgId = orgid;
		var custid = $("#txtcustId").val();
		if (custid == "" || custid == undefined) {
			$.messager.alert("操作提示", "请选择客户！", "error");
		}
		chargeObj.custId = custid;
		var money = $("#txtmoney").numberbox('getValue');
		chargeObj.money = money;
		var chargetime = $("#txtrechargeTime").datetimebox("getValue");
		chargeObj.rechargeTime = chargetime;
		chargeObj.rechargeDesc = $("#txtrechargeDesc").val();
		$.ajax({
			url : "charge/saveRechargeObj.do",
			type : "POST",
			dataType : "json",
			async : false,
			data : chargeObj,
			success : function(req) {
				parent.ChargeManage.SearchAction();
				parent.m_charge_dlg.close();
			},
			failer : function(a, b) {
				$.messager.alert("消息提示", a, "info");
			},
			error : function(a) {
				$.messager.alert("消息提示", a, "error");
			}
		});
	},
	CancelSaveAction : function() {
		$("#txtcustName").val("");
		$("#txtcustId").val("");
		$("#txtcustPhone").val("");
		$("#txtcustEmail").val("");
		$("#txtmoney").numberbox('setValue', 0.00);
		$("#txtrechargeTime").datebox("setValue", "");
		$("#txtrechargeDesc").val("");
		parent.m_charge_dlg.close();
	},
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
			onDblClickRow : ChargeBillManage.SelectCustUserAction,
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
	SelectCustUser : function() {
		m_chargeuser_dlg = art.dialog({
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
	},
	SearchCustUser : function() {
		$('#custUserGrid').datagrid("reload", {
			"name" : $("#search_custname").val()
		});
	},
	SelectCustUserAction : function(index, rowData) {
		$("#txtcustId").val(rowData.id);
		$("#txtcustName").val(rowData.name);
		$("#txtcustPhone").val(rowData.phone);
		$("#txtcustEmail").val(rowData.email);
		m_chargeuser_dlg.close();
	}
};

function getCurrentUserInfo() {
	$.ajax({
		url : "index/isSignIn.do",
		type : "POST",
		dataType : "json",
		async : false,
		success : function(req) {
			if (req.isSuccess) {
				$("#txtorgName").val(req.data.orgName);
				$("#txtorgId").val(req.data.orgId);
			} else {
				$.messager.alert("系统提示", "获取用户登录信息失败，请重新登录", "info");
				top.location.href = "login.jsp";
			}
		}
	});
}
