var m_custuser_dlg;
var m_birthday_record_dlg;
var m_custuser_type;
var m_custuser_query = { 
	custname : "" ,
	phone:""
};

var m_customer_viewType;

var m_custuser_object = {};
$(function() {
	var args = getUrlArgs();
	m_customer_viewType=parseInt( args["viewType"]);
	
	viewSwitch();
	

	$('#custUserGrid').datagrid({
		url : 'customer/getcustList.do',
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
			align : 'left',
			width : 150
		},  {
			title : '性别',
			field : 'sex',
			align : 'center',
			width : 150,
			formatter:function(value,rowData,index){
				if(value==0||value=="0"){
					return "男";
				}else{
					return "女";
				}
			}
		},{
			title : '门店信息',
			field : 'orgName',
			width : 150,
			align : 'left'
		},{
			title : '联系方式',
			field : 'phone',
			width : 150,
			align : 'left'
		},{
			title : '出生日期',
			field : 'birthday',
			width : 150,
			align : 'left'
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
	$("#btnBirthdayRecord").bind("click", onRecordBirthday);
	$("#btnBirthdayLog").bind("click", onLogBirthday);
	$("#btnSearch").bind("click", CustUserManage.SearchAction);
});


function loadCustomer(){
	$.ajax({
		url : "custuser/getCustomer.do",
		type : "POST",
		dataType : "json",
		async : false,
		success : function(req) {
			if (req.isSuccess) {
				var nodes = buildTreeOrg(req.rows);
				$('#OrganizationTree').treegrid("loadData", nodes);
			} 
		}
	});
}

function viewSwitch(){
	if(m_customer_viewType ==1){
		$('#btnBirthdayRecord').hide();
		$('#btnBirthdayLog').hide();
	}
}


function  onRecordBirthday(){
	
	var row = $("#custUserGrid").datagrid("getSelected");
	
	if(row){
		try {
			m_birthday_record_dlg = art
					.dialog({
						id : 'dlgBirthdayRecord',
						title : '生日提示',
						content : "<iframe scrolling='yes' frameborder='0' src='view/base/customerBirthdayRecord.jsp?id="+row.id+"' style='width:500px;height:380px;overflow:hidden'/>",
						// content:"123",
						lock : true,
						initFn : function() {
						}
					});
		} catch (ex) {
			alert(ex);
		}
	}
}

function onLogBirthday(){
	
}

function onSelRow(){
	
}

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
						content : "<iframe scrolling='yes' frameborder='0' src='view/base/customerBill.jsp?optType="+optType+"' style='width:500px;height:420px;overflow:hidden'/>",
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
		m_custuser_query.phone = $.trim($("#sch_phone").val()); 
		m_custuser_query.custname = $.trim($("#sch_custname").val()); 
	}
	
	
	
	
};
