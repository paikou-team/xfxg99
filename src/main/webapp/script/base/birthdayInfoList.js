var m_custuser_dlg;
var m_birthday_record_dlg;
var m_custuser_type;
var m_customer;
var m_birthdayInfo_query = {
	orgName : "",
	custName : ""
};

var m_customer_viewType;

var m_custuser_object = {};
$(function() {
	var args = getUrlArgs();
	m_customer_viewType=parseInt( args["viewType"]);
	
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

	$('#dgBirthdayInfo').datagrid({
		url : 'customer/getBirthdayInfoList.do',
		queryParams : {
			'birthdayInfoQuery' : JSON.stringify(m_birthdayInfo_query)
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
		},{
			title : '到期时间',
			field : 'period',
			align : 'right',
			width : 80,
			formatter:function(value,row,index){
				var p=parseInt(row.period);
				var ap=Math.abs(p);
				var text=null;
				
				if(p==0){
					text="今天";
				}else if(p==1){
					text="明天";
				}else if(p==2){
					text="后天";
				}else if(p==-1){
					text ="昨天";
				}else if(p==-2){
					text="前天";
				}else if(p>2){
					text ="还有"+ ap +"天";
				}else if(p<-2){
					text ="已过"+ ap +"天";
				}
				return text;
				
			}
		},{
			title : '问候',
			field : 'blessDescription',
			align : 'left',
			width : 160
		},{
			title : '客户姓名',
			field : 'name',
			align : 'left',
			width : 150
		},  {
			title : '性别',
			field : 'sex',
			align : 'left',
			width : 60,
			hidden: true,
			formatter:function(value,rowData,index){
				if(value==0||value=="0"){
					return "男";
				}else if(value==1||value=="1"){
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
		} ] ],
		rowStyler:function(index,row){
			if(row.period < 1 && row.blessId==0){
				return 'color:red;';
			}
		}
	});
	$("#ShowCustUserInfo").bind("click", CustUserManage.ShowCustUser);
	$("#btnBirthdayRecord").bind("click", onRecordBirthday);
	$("#btnBirthdayLog").bind("click", onLogBirthday);
	// $("#ShowChargeInfo").bind("click", CustUserManage.ShowCharge);
});


function onBirthdayInfoSearch(){
	m_birthdayInfo_query.orgName = $('#txtShopName').val();
	m_birthdayInfo_query.custName =$('#txtCustName').val();
	
	var json = JSON.stringify(m_birthdayInfo_query);
    $('#dgBirthdayInfo').datagrid('reload', { 'birthdayInfoQuery': json });
}



function  onRecordBirthday(){
	
	var row = $("#dgBirthdayInfo").datagrid("getSelected");
	
	if(row){
		m_customer=row;
		try {
			m_birthday_record_dlg = art
					.dialog({
						id : 'dlgBirthdayInfo',
						title : '生日祝福',
						content : "<iframe scrolling='yes' frameborder='0' src='view/base/birthdayInfo.jsp?id="+row.id+"&year=0' style='width:480px;height:200px;overflow:hidden'/>",
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

function onCustomer(){
	try {
		m_custuser_dlg = art
				.dialog({
					id : 'dlgCustUserBill',
					title : '客户资料',
					content : "<iframe scrolling='yes' frameborder='0' src='view/base/custuserBill.jsp?optType=0' style='width:480px;height:380px;overflow:hidden'/>",
					// content:"123",
					lock : true,
					initFn : function() {
					}
				});
	} catch (ex) {
		alert(ex);
	}
}

var CustUserManage = {
	ShowCustUser : function() {
		var hasRows = $('#dgBirthdayInfo').datagrid('getRows');
		if (hasRows.length == 0) {
			$.messager.alert('操作提示', "没有可操作数据", "warning");
			return;
		}
		var target = $("#dgBirthdayInfo").datagrid("getChecked");
		if (!target || target.length == 0) {
			$.messager.alert('操作提示', "请选择操作项!", "warning");
			return;
		}
		if (target.length > 1) {
			$.messager.alert('操作提示', "只能选择单个操作项!", "warning");
			return;
		}
		CustUserManage.packageObject(target[0]);
		CustUserManage.ShowDialog(0);
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
		m_custuser_object.orgName = obj.orgName;
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
		m_custuser_query.orgname = $.trim($("#sch_orgname").val());
		m_custuser_query.recname = $.trim($("#sch_recname").val());
		m_custuser_query.custname = $.trim($("#sch_custname").val());
		m_custuser_query.isused = $("#sch_isUsed").combobox("getValue");
	}
	
	
	
	
};