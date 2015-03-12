var chargeObj = {};
var opType;
$(function() {

	var args = getUrlArgs();

	var typeid = args.type;
	if (typeid == 0 || typeid == "0") {
		opType = "add";
		if (g_current_user != undefined) {
			$("#txtorgName").val(g_current_user.orgName);
			$("#txtorgId").val(g_current_user.orgId);
		}else{
			
		}
		$("#txtcustName").val("");
		$("#txtmoney").numberbox('setValue', 0.00);
		$("#txtrechargeTime").datetimebox('setValue', '6/1/2012 12:30:56');
		$("#txtrechargeDesc").val("");

	} else {
		opType = "edit";
		$("#tb_operation").hide();
		var score = parent.m_charge_object;
		$("#txtorgName").val(score.orgName);
		$("#txtcustName").val(score.custName);
		$("#txtmoney").numberbox('setValue', score.money);
		$("#txtrechargeTime").datetimebox('setValue', score.rechargeTime);
		$("#txtrechargeDesc").val(score.rechargeDesc);
	}

	chargeObj.id = 0;

	$("#btnSaveChargeInfo").bind("click", ChargeBillManage.SaveChargeAction);
	$("#btnCancelSave").bind("click", ChargeBillManage.CancelSaveAction);
});

function getCurrentTimeWithMins(){
	var myDate = new Date();   
    var currentTime = myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate()+" "+myDate.getHours()+":"+myDate.getMinutes();
    return currentTime;
}
var ChargeBillManage = {
	SaveChargeAction : function() {
		var orgid = $("#txtorgId").val();
		chargeObj.orgId = orgid;
		var custid = $("#txtcustId").val();
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
				parent.art.dialog.list['dlgChargeBill'].close();
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
		$("#txtorgName").combobox("setValue", "");
		$("#txtcustName").combobox("setValue", "");
		$("#txtmoney").numberbox('setValue', 0.00);
		$("#txtrechargeTime").datebox("setValue", "");
		$("#txtrechargeDesc").val("");
		parent.art.dialog.list['dlgChargeBill'].close();
	}
};