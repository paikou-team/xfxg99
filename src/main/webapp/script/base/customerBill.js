var custUserObj = {};
var myDate ;
$(function() {

	var args = getUrlArgs();
	myDate = new Date();
	// var myDate = new Date();
	// var typeid = args.optType;
	// if (typeid == 0 || typeid == "0") {
	// opType = "add";
	// getCurrentUserInfo();
	// custUserObj.id = 0;
	// $("#txtid").val("");
	// $("#txtcustName").val("");
	// $("#txtmoney").numberbox('setValue', 0.00);
	// $("#txtrechargeTime").datetimebox(
	// 'setValue',
	// myDate.getFullYear() + "-" + (myDate.getMonth() + 1) + "-"
	// + myDate.getDate() + " " + myDate.getHours() + ":"
	// + myDate.getMinutes());
	// $("#txtrechargeDesc").val("");
	//      
	// } else {
	// opType = "edit";
	// $("#tb_operation").hide();
	// $("#btnSelectCustUser").hide();
	var score = parent.m_custuser_object;

	$("#txtid").val(score.id);
	$("#txtcustname").val(score.name);
	$("#txtcustbirth").datebox("setValue", score.birthday);
	$("#txtcustsex").combobox("setValue", score.sex);
	$("#txtcustphone").val(score.phone);
	$("#txtcustemail").val(score.email);
	$("#txtrecuser").val(score.recUser);
	$("#txtorgname").val(score.orgName);
	$("#txtrealname").val(score.realname);
	// }
 
	$("#btnCancelSave").bind("click", CustUserBillManage.CancelSaveAction);

});

var CustUserBillManage = {
	SaveCustUserAction : function() {
		var custid = $("#txtid").val();
		if (custid == "" || custid == undefined) {
			$.messager.alert("操作提示", "获取客户资料信息失败，请重新登录！", "error");
			top.location.href = "login.jsp";
		}
		custUserObj.id = custid;
		var custname = $.trim($("#txtcustname").val());
		if (custname == "" || custname == undefined) {
			$.messager.alert("操作提示", "客户姓名必须填写！", "error");
			return;
		}
		custUserObj.birthday = $("#txtcustbirth").datebox("getValue");
		if ($("#txtcustsex").combobox("getValue") == ""
				|| $("#txtcustsex").combobox("getValue") == undefined) {
			custUserObj.sex = 1;
		} else {
			custUserObj.sex = $("#txtcustsex").combobox("getValue");
		}
		custUserObj.name = custname;
		custUserObj.phone = $.trim($("#txtcustphone").val());
		custUserObj.email = $.trim($("#txtcustemail").val());
		custUserObj.recUser = $.trim($("#txtrecuser").val());
		// custUserObj.orgName = $.trim($("#txtorgname").val());

		$.ajax({
			url : "customer/saveCustUserObj.do",
			type : "POST",
			dataType : "json",
			async : false,
			data : custUserObj,
			success : function(req) {
				if (req.isSuccess) {
					parent.CustUserManage.SearchAction();
					parent.m_custuser_dlg.close();
				} else {
					$.messager.alert("系统提示", req.msg, "error");
				}
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

		$("#txtid").val("");
		$("#txtcustname").val("");
		$("#txtcustbirth").datebox("setValue",myDate.getFullYear() + "-" + (myDate.getMonth() + 1) + "-"+ myDate.getDate());
		$("#txtcustsex").combobox("setValue", 1);
		$("#txtcustphone").val("");
		$("#txtcustemail").val("");
		$("#txtrecuser").val("");
		$("#txtorgname").val("");
		parent.m_custuser_dlg.close();
	}
};
//
// function getCurrentUserInfo() {
// $.ajax({
// url : "index/isSignIn.do",
// type : "POST",
// dataType : "json",
// async : false,
// success : function(req) {
// if (req.isSuccess) {
// $("#txtorgName").val(req.data.orgName);
// $("#txtorgId").val(req.data.orgId);
// } else {
// $.messager.alert("系统提示", "获取用户登录信息失败，请重新登录", "info");
// top.location.href = "login.jsp";
// }
// }
// });
// }
