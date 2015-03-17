var m_sysparam_obj = {};

$(function() {
	$("#txtparamedittype").val("text");
	$("#txtparamtype").combobox("setValue", "业务");
	$("#btnSaveParamInfo").bind("click", ParamBillManage.SaveParamInfo);
	$("#btnCancelSave").bind("click", ParamBillManage.CancelSave);
	m_sysparam_obj.id = 0;
});

var ParamBillManage = {
	SaveParamInfo : function() {
		var paramtype = $("#txtparamtype").combobox("getValue");
		if (paramtype == "" || paramtype == undefined) {
			$.messager.alert("操作提示", "请选择参数类型", "error");
			return;
		}
		m_sysparam_obj.groupname = paramtype;
		var paramname = $.trim($("#txtparamname").val());
		if (paramname.length == 0) {
			$.messager.alert("操作提示", "请输入参数名称", "error");
			return;
		}
		m_sysparam_obj.name = paramname;
		var paramkey = $.trim($("#txtparamkey").val());
		if (paramkey.length == 0) {
			$.messager.alert("操作提示", "请输入参数Key", "error");
			return;
		}
		m_sysparam_obj.paramkey = paramkey
		var paramvalue = $.trim($("#txtparamvalue").val());
		if (paramvalue.length == 0) {
			$.messager.alert("操作提示", "请输入参数值", "error");
			return;
		}
		m_sysparam_obj.value = paramvalue;
		m_sysparam_obj.editor = "text";

		$.ajax({
			url : "sysparam/saveSysParamObj.do",
			type : "POST",
			dataType : "json",
			async : false,
			data : m_sysparam_obj,
			success : function(req) {
				if (req.isSuccess) {
					parent.SysParamManage.RefreshAction();
					parent.m_sysparam_dlg.close();
				} else {
					$.messager.alert("系统提示", req.msg, "error")
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
	CancelSave : function() {
		$("#txtparamtype").combobox("setValue", 0);
		$("#txtparamname").val("");
		$("#txtparamkey").val("");
		$("#txtparamvalue").val("");
		parent.m_sysparam_dlg.close();
	}
};