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
		 <p style="padding:0px;border-bottom:1px solid black;">
		 	<span  id="tb_operation"><a id="btnSaveChargeInfo"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_save" plain="true">确定</a></span>
		 	<a id="btnCancelSave"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_close" plain="true">取消</a>  
		 </p>
		 <p style="padding:6px"><label>门店信息：</label><input id="txtorgName" class="easyui-validatebox" readonly="readonly" style="width:324px" /><input id="txtorgId" type="hidden"/></p>
		 <p style="padding:6px"><label>充值金额：</label><input id="txtmoney" type="text" class="easyui-numberbox"  data-options="required:true,min:0,precision:2"  style="width:324px" /></p> 
		 <p style="padding:6px"><label>充值时间：</label><input id="txtrechargeTime" class="easyui-datetimebox" data-options="required:true,editable:false,showSeconds:false" style="width:324px"  /></p> 
		 
		 <p style="padding:6px">
		 	<label>客户姓名：</label><input id="txtcustName" class="easyui-validatebox"  readonly="readonly"   style="width:324px"  /><input id="txtcustId" type="hidden"/>
		 	<a id="btnSelectCustUser"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_search" plain="true">客户选择</a>
		 </p>	
		 <p style="padding:6px"><label>联系方式：</label><input id="txtcustPhone" class="easyui-validatebox"  readonly="readonly"   style="width:324px"  /> </p>
		 <p style="padding:6px"><label>电子邮件：</label><input id="txtcustEmail" class="easyui-validatebox"  readonly="readonly"    style="width:324px"  /> </p>
		 <p style="padding:6px"><label>充值备注：</label><textarea id="txtrechargeDesc" type="textarea" style="width:324px;height:40px" ></textarea></p> 
	</div>
	<div id="div_custuser" style="width:500px;height:300px; display:none">
		<div id="custUserGrid" fit="true">
		
		</div>
		<div id="tb_custUser">
			<label>客户姓名:</label><input id="search_custname" class="easyui-validatebox" style="width:120px" />
			<a id="btnSearchCustUser"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_search" plain="true">查询</a>
		</div>
	</div>
</body>
</html>
