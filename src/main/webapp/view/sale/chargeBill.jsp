<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<script src='<%=basePath%>script/sale/chargeBill.js'
	type='text/javascript'></script>
<title>充值单据</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>

	<div style="text-align:left;overflow:hidden;">
		 <p id="tb_operation" style="padding:0px;border-bottom:1px solid black;">
		 	<a id="btnSaveChargeInfo"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-save" plain="true">确定</a>
			<a id="btnCancelSave"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-cancel" plain="true">取消</a> 
		 </p>
		 <p style="padding:6px"><label>门店信息：</label><input id="txtorgName" class="easyui-combobox" data-options="required:true,editalbe:false" style="width:184px" /></p>
		 <p style="padding:6px"><label>注册用户：</label><input id="txtcustName" class="easyui-combobox" data-options="required:true,editalbe:false"  style="width:184px"  /></p>
		 <p style="padding:6px"><label>充值金额：</label><input id="txtmoney" type="text" class="easyui-numberbox"  data-options="required:true,min:0,precision:2"  style="width:184px" /></p> 
		 <p style="padding:6px"><label>充值时间：</label><input id="txtrechargeTime" class="easyui-datetimebox" data-options="required:true,showSeconds:false" style="width:184px"  /></p> 
		 <p style="padding:6px"><label>充值备注：</label><textarea id="txtrechargeDesc" type="textarea" style="width:184px;height:80px" ></textarea></p> 
	</div>
</body>
</html>
