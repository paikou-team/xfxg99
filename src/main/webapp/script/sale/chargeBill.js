var chargeObj = {};

$(function() {
	
	var args = getUrlArgs();
	
	chargeObj.id = 0;
	$("#txtorgName").combobox({
		url:"charge/getorganList.do",
        valueField: 'id',
        textField: 'name',
        editable: false,
        panelHeight: 180,
        onSelect: function (record) { chargeObj.orgId = record.id; }
	});
	$("#txtcustName").combobox({
		url:"charge/getcustList.do",
        valueField: 'id',
        textField: 'name',
        editable: false,
        panelHeight: 180,
        onSelect: function (record) { chargeObj.custId = record.id; }
    }); 
	$("#txtmoney").numberbox('setValue', 0.00); 
	$("#txtrechargeTime").datebox();
	$("#btnSaveChargeInfo").bind("click", ChargeBillManage.SaveChargeAction);
	$("#btnCancelSave").bind("click", ChargeBillManage.CancelSaveAction);  
});

var ChargeBillManage ={
		SaveChargeAction:function(){
			var money = $("#txtmoney").numberbox('getValue');
			chargeObj.money = money;
			var chargetime = $("#txtrechargeTime").datebox("getValue"); 
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
		CancelSaveAction:function(){ 
			$("#txtorgName").combobox("setValue","");
			$("#txtcustName").combobox("setValue","");
			$("#txtmoney").numberbox('setValue', 0.00); 
			$("#txtrechargeTime").datebox("setValue",""); 
			$("#txtrechargeDesc").val(""); 
			parent.art.dialog.list['dlgChargeBill'].close();
		} 
};