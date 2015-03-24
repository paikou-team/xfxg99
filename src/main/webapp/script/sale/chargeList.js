var m_charge_dlg;
var m_charge_type;
var m_charge_query = {
	orgname : "",
	custname : "",
	//username : "",
	isconfirm : 0
};
var m_charge_object = {};
$(function() {
	var args = getUrlArgs();

	var optType = args.optType;
	if (optType == 0 || optType == "0") {// 门店--客户充值，只有新增、查看操作
		$("#ConfirmCharge").hide();
	} else if (optType == 1 || optType == "1")// 商务中心--充值记录，只有确认、查看操作
	{
		$("#AddCharge").hide();
	} else {
		$("#ConfirmCharge").hide();
		$("#AddCharge").hide();
		$("#tb_searchbox").hide();
		m_charge_query = {
			orgname : "",
			custname : "",
			//username : "",
			isconfirm : 2
		};
	}

	$('#chargeGrid').datagrid({
		url : 'charge/getList.do',
		queryParams : {
			'charge_Query' : JSON.stringify(m_charge_query)
		},
		fitColumns : true,
		rownumbers : true,
		resizable : true,
		pagination : true,
		pageNumber : 1,
		pageSize : 20,
		nowrap : false,
		idField : 'id',
		singleSelect : true,
		onDblClickRow : onSelRow,
		toolbar : "#chargeTb",
		columns : [ [ {
			title : 'Id',
			field : 'id',
			align : 'left',
			width : 5,
			hidden : true
		}, {
			title : '充值状态',
			field : 'isConfirm',
			align : 'center',
			width : 100,
			formatter : function(value, row, index) {
				if (row.confirmUserId > 0) {
					return "已确认";
				} else {
					return "未确认";
				}
			}
		}, {
			title : '注册用户',
			field : 'custName',
			width : 150,
			align : 'center'
		}, {
			title : '电子邮件',
			field : 'custEmail',
			align : 'center',
			width : 200
		}, {
			title : '联系方式',
			field : 'custPhone',
			align : 'center',
			width : 150
		}, {
			title : '门店信息',
			field : 'orgName',
			align : 'center',
			width : 250
		}, {
			title : '充值金额',
			field : 'money',
			align : 'center',
			width : 120
		}, {
			title : '充值时间',
			field : 'rechargeTime',
			align : 'center',
			width : 250
		}, {
			title : '确认人',
			field : 'userName',
			align : 'center',
			width : 120
		}, {
			title : '确认时间',
			field : 'confirmTime',
			align : 'center',
			width : 250
		}, {
			title : '充值描述',
			field : 'rechargeDesc',
			align : 'center',
			width : 150
		} ] ]
	});
	$("#AddCharge").bind("click", ChargeManage.AddCharge);
	$("#btnSearch").bind("click", ChargeManage.SearchAction);
	$("#ConfirmCharge").bind("click", ChargeManage.ConfirmCharge);
	$("#ShowChargeInfo").bind("click", ChargeManage.ShowCharge);
});

function onSelRow(rowIndex, rowData) {
	ChargeManage.packageObject(rowData);
	ChargeManage.ShowDialog();
}

var ChargeManage = {
	AddCharge : function() {
		
		if(!checkAuthorize2("cust_recharge_add")){
			return;
		}
		
		try {
			m_charge_dlg = art
					.dialog({
						id : 'dlgChargeBill',
						title : '充值单据',
						content : "<iframe scrolling='yes' frameborder='0' src='view/sale/chargeBill.jsp?type=0' style='width:600px;height:470px;overflow:hidden'/>",
						// content:"123",
						lock : true,
						initFn : function() {
						}
					});
		} catch (ex) {
			alert(ex);
		}
	},
	ShowCharge : function() {
		var hasRows = $('#chargeGrid').datagrid('getRows');
		if (hasRows.length == 0) {
			$.messager.alert('操作提示', "没有可操作数据", "warning");
			return;
		}
		var target = $("#chargeGrid").datagrid("getChecked");
		if (!target || target.length == 0) {
			$.messager.alert('操作提示', "请选择操作项!", "warning");
			return;
		}
		if (target.length > 1) {
			$.messager.alert('操作提示', "只能选择单个操作项!", "warning");
			return;
		}
		ChargeManage.packageObject(target[0]);
		ChargeManage.ShowDialog();
	},
	packageObject : function(obj) {
		m_charge_object.id = obj.id;
		m_charge_object.custName = obj.custName;
		m_charge_object.orgName = obj.orgName;
		m_charge_object.money = obj.money;
		m_charge_object.rechargeTime = obj.rechargeTime;
		m_charge_object.rechargeDesc = obj.rechargeDesc;
		m_charge_object.custEmail = obj.custEmail;
		m_charge_object.custPhone = obj.custPhone;
	},
	ShowDialog : function() {
		try {
			m_charge_dlg = art
					.dialog({
						id : 'dlgChargeBill',
						title : '充值单据',
						content : "<iframe scrolling='yes' frameborder='0' src='view/sale/chargeBill.jsp?type=1' style='width:500px;height:450px;overflow:hidden'/>",
						// content:"123",
						lock : true,
						initFn : function() {
						}
					});
		} catch (ex) {
			alert(ex);
		}
	},
	ConfirmCharge : function() {
		
		if(!checkAuthorize2("cust_recharge_add")){
			return;
		}
		
		
		var hasRows = $('#chargeGrid').datagrid('getRows');
		if (hasRows.length == 0) {
			$.messager.alert('操作提示', "没有可操作数据", "warning");
			return;
		}
		var target = $("#chargeGrid").datagrid("getChecked");
		if (!target || target.length == 0) {
			$.messager.alert('操作提示', "请选择操作项!", "warning");
			return;
		}
		if (target.length > 1) {
			$.messager.alert('操作提示', "只能选择单个操作项!", "warning");
			return;
		}
		if (target[0].userName.length > 0 || target[0].confirmUserId > 0) {
			$.messager.alert("操作提示", "用户" + target[0].custName
					+ "的充值信息已确认，请勿重复操作！", "warning");

		} else {
			$.messager.confirm("充值信息确认", "充值用户 :" + target[0].custName
					+ " 充值金额  :" + target[0].money + " ？", function(r) {
				if (r) {
					ChargeManage.ConfirmChargeAction(target[0].id);
				}
			});
		}
	},
	ConfirmChargeAction : function(id) {
		$.ajax({
			url : "charge/confirmRecharge.do?id=" + id,
			type : "POST",
			dataType : "json",
			async : false,
			success : function(req) {
				$.messager.alert("系统提示", req.msg, "info");
				$('#chargeGrid').datagrid("reload");
			},
			failer : function(a, b) {
				$.messager.alert("消息提示", a, "info");
			},
			error : function(a) {
				$.messager.alert("消息提示", a, "error");
			}
		});
	},
	SearchAction : function() {
		ChargeManage.packQuery();
		var json = JSON.stringify(m_charge_query);
		$('#chargeGrid').datagrid("reload", {
			"charge_Query" : json
		});
	},
	packQuery : function() {
		m_charge_query.orgname = $.trim($("#sch_orgname").val());
		m_charge_query.custname = $.trim($("#sch_custname").val());
		//m_charge_query.username = $.trim($("#sch_username").val());
		if ($("#sch_isconfirm").combobox("getValue") == null
				|| $("#sch_isconfirm").combobox("getValue") == undefined
				|| $("#sch_isconfirm").combobox("getValue") == "") {
			m_charge_query.isconfirm = 0;
		} else {
			m_charge_query.isconfirm = $("#sch_isconfirm").combobox("getValue");
		}
	}
};