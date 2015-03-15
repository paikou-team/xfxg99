var m_custuser_dlg;
var m_custuser_type;
var m_custuser_query = {
	orgname : "",
	custname : "",
	recUser : "",
	isused : 0
};
var m_custuser_object = {};
$(function() {
	var args = getUrlArgs();

	// var optType = args.optType;
	// if (optType == 0 || optType == "0") {// 门店--客户充值，只有新增、查看操作
	// $("#ConfirmCharge").hide();
	// } else if (optType == 1 || optType == "1")// 商务中心--充值记录，只有确认、查看操作
	// {
	// $("#AddCharge").hide();
	// } else {
	// //$("#ConfirmCharge").hide();
	// $("#AddCharge").hide();
	// $("#tb_searchbox").hide();
	// m_charge_query = {
	// orgname : "",
	// custname : "",
	// username : "",
	// isconfirm : 2
	// };
	// }

	$('#custUserGrid').datagrid({
		url : 'custuser/getcustList.do',
		queryParams : {
			'custuser_query' : JSON.stringify(m_custuser_query)
		},
		fitColumns : true,
		rownumbers : true,
		resizable : true,
		pagination : true,
		pageNumber : 1,
		pageSize : 10,
		nowrap : false,
		idField : 'id',
		singleSelect : true,
		onDblClickRow : CustUserManage.ShowCustUserAction,
		toolbar : "#custUserTb",
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
		},  {
			title : '性别',
			field : 'sex',
			align : 'center',
			width : 150,
			formatter:function(value,rowData,index){
				if(value==0||value=="0"){
					return "女";
				}else{
					return "男";
				}
			}
		},{
			title : '联系方式',
			field : 'phone',
			width : 150,
			align : 'center'
		},{
			title : '出生日期',
			field : 'birthday',
			width : 150,
			align : 'center'
		}, {
			title : '电子邮件',
			field : 'email',
			align : 'left',
			width : 250
		}, {
			title : '推荐人',
			field : 'recUser',
			align : 'left',
			width : 250
		} ] ]
	});
	$("#ShowCustUserInfo").bind("click", CustUserManage.ShowCustUser);
	// $("#ShowChargeInfo").bind("click", CustUserManage.ShowCharge);
});

var CustUserManage = {
	ShowCustUser : function() {
		var hasRows = $('#custUserGrid').datagrid('getRows');
		if (hasRows.length == 0) {
			$.messager.alert('操作提示', "没有可操作数据", "warning");
			return;
		}
		var target = $("#custUserGrid").datagrid("getChecked");
		if (!target || target.length == 0) {
			$.messager.alert('操作提示', "请选择操作项!", "warning");
			return;
		}
		if (target.length > 1) {
			$.messager.alert('操作提示', "只能选择单个操作项!", "warning");
			return;
		}
		CustUserManage.packageObject(target[0]);
		CustUserManage.ShowDialog();
	},
	ShowCustUserAction : function(index, rowData) { 
		CustUserManage.packageObject(rowData);
		CustUserManage.ShowDialog(0);
	},
	packageObject : function(obj) {
		m_custuser_object.id = obj.id;
		m_custuser_object.name = obj.name;
		m_custuser_object.sex = obj.sex;
		m_custuser_object.birthday = obj.birthday;
		m_custuser_object.phone = obj.phone;
		m_custuser_object.email = obj.email;
		m_custuser_object.recUser = obj.recUser; 
	},
	ShowDialog : function(optType) {
		try {
			m_custuser_dlg = art
					.dialog({
						id : 'dlgCustUserBill',
						title : '客户资料',
						content : "<iframe scrolling='yes' frameborder='0' src='view/base/custuserBill.jsp?optType="+optType+"' style='width:500px;height:380px;overflow:hidden'/>",
						// content:"123",
						lock : true,
						initFn : function() {
						}
					});
		} catch (ex) {
			alert(ex);
		}
	}, 
	SearchAction : function() {
		CustUserManage.packQuery();
		var json = JSON.stringify(m_custuser_query);
		$('#custUserGrid').datagrid("reload", {
			"custuser_query" : json
		});
	},
	packQuery : function() {
		m_custuser_query.orgname = $.trim($("#sch_orgname").val());
		m_custuser_query.recname = $.trim($("#sch_recname").val());
		m_custuser_query.custname = $.trim($("#sch_custname").val());
		m_custuser_query.isused = $("#sch_isUsed").combobox("getValue");
	}
};