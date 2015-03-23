var m_sysparam_dlg;

$(function() {
	$("#sysParamGrid").propertygrid({
		url : 'sysparam/getSysParamList.do',
		showGroup : true,
		scrollbarSize : 0,
		title : "系统参数设置",
		toolbar : "#sysParamTb",
		columns : [ [ {
			field : 'id',
			title : 'ID',
			width : 100,
			hidden : true
		}, {
			field : 'name',
			title : '参数名称',
			width : 100
		}, {
			field : 'paramkey',
			title : '参数关键字',
			width : 100,
			hidden : true
		}, {
			field : 'value',
			title : '参数值',
			width : 500
		}, {
			field : 'groupname',
			title : '类别',
			width : 100,
			hidden : true
		}, {
			field : 'editor',
			title : '编辑类型',
			width : 100,
			hidden : true
		} ] ]
	});
//	$("#AddParam").bind("click", SysParamManage.AddNewParam);
	$("#SaveParamInfo").bind("click", SysParamManage.SaveParamInfo);
});

var SysParamManage = {
	AddNewParam : function() {
		try {
			m_sysparam_dlg = art
					.dialog({
						id : 'dlgSysParamBill',
						title : '参数设置',
						content : "<iframe scrolling='yes' frameborder='0' src='view/base/sysparamBill.jsp' style='width:500px;height:380px;overflow:hidden'/>",
						// content:"123",
						lock : true,
						initFn : function() {
						}
					});
		} catch (ex) {
			alert(ex);
		}
	},
	SaveParamInfo : function() {
		var s = $("#sysParamGrid").propertygrid("getData");
		if (s.rows.length > 0) {
			var SysParamObj = [];
			for ( var i = 0; i < s.rows.length; i++) {
				var paramobj = {};
				paramobj.id = s.rows[i].id;
				paramobj.name = s.rows[i].name;
				paramobj.paramkey = s.rows[i].paramkey;
				paramobj.value = s.rows[i].value;
				paramobj.groupname = s.rows[i].groupname;
				paramobj.editor = s.rows[i].editor;
				SysParamObj.push(paramobj);
			}
			var JsonStr = JSON.stringify(SysParamObj);
			$
					.ajax({
						url : "sysparam/saveSysParamList.do",
						type : "POST",
						dataType : "json",
						async : false,
						data : "dataStr=" + JsonStr,
						success : function(req) {
							if (req.isSuccess) { 
								SysParamManage.RefreshAction();
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

		}
	},
	RefreshAction : function() {
		$("#sysParamGrid").propertygrid("reload");
	}
};