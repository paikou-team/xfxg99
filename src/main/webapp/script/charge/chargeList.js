var m_charge_dlg;
var m_charge_type;
var m_charge_query = {
	orgname : "",
	custname : "",
	username : "",
	isconfirm : 0
};

$(function() {
	var args = getUrlArgs();

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
			width : 150,
			formatter : function(value, row, index) {
				if (value.length>0&&value) {
					return "已确认";
				} else {
					return "未确认";
				}
			}
		}, {
			title : '注册用户',
			field : 'userName',
			width : 150,
			align : 'center'
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
			width : 150,
			formatter:function(value){
				return "a";
			}
		}, {
			title : '确认人',
			field : 'confirmUserName',
			align : 'center',
			width : 120
		}, {
			title : '确认时间',
			field : 'confirmTime',
			align : 'center',
			width : 150,
			formatter:function(value){
				return "a";
			}
		} , {
			title : '充值描述',
			field : 'rechargeDesc',
			align : 'center',
			width : 150
		}] ]
	});
	$("#AddCharge").bind("click", ChargeManage.AddCharge);
	$("#btnSearch").bind("click", ChargeManage.SearchAction);
	$("#ConfirmCharge").bind("click", ChargeManage.ConfirmCharge);
	// $("#DelUser").bind("click", UserManage.DelUser);
});

function onSelRow() {

}

var ChargeManage = { 
	AddCharge : function() {
		try {
			m_charge_dlg = art
					.dialog({
						id : 'dlgChargeBill',
						title : '充值单据',
						content : "<iframe scrolling='yes' frameborder='0' src='view/charge/chargeBill.jsp' style='width:400px;height:450px;overflow:hidden'/>",
						// content:"123",
						lock : true,
						initFn : function() {
						}
					});
		} catch (ex) {
			alert(ex);
		}
	},
	ConfirmCharge:function(){
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
		if(target[0].confirmUserName.length>0){
			$.messager.alert("操作提示","用户"+target[0].userName+"的充值信息已确认，请勿重复操作！","warning");
			
		}else{
			$.messager.confirm("充值信息确认", "充值用户 :"+target[0].userName+" 充值金额  :"+target[0].money +" ？", function(r) {
				if (r) {
					ChargeManage.ConfirmChargeAction(target[0].id);
				}
			});
		}
	},
	ConfirmChargeAction:function(id){
		
	},
	SearchAction : function() {
		ChargeManage.packQuery();
		var json = JSON.stringify(m_charge_query);
		$('#chargeGrid').datagrid("reload", {
			"charge_Query" : json
		});
	},
	packQuery : function() {
		var m_charge_query={};
		m_charge_query.orgname = $.trim($("#sch_orgname").val());
		m_charge_query.custname = $.trim($("#sch_username").val());
		m_charge_query.username = $.trim($("#sch_custname").val());
		m_charge_query.isconfirm = $("#sch_isconfirm").combobox("getValue");
	}
};