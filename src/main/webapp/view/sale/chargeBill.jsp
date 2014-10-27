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
		 <p><label>门店信息：</label><input id="txtorgName" class="easyui-validatebox" readonly="readonly" style="width:324px" /><input id="txtorgId" type="hidden"/></p>
		 <p><label>充值金额：</label><input id="txtmoney" type="text" class="easyui-numberbox"  data-options="required:true,min:0,precision:2"  style="width:324px" /></p> 
		 <p><label>充值时间：</label><input id="txtrechargeTime" class="easyui-datetimebox" data-options="required:true,editable:false,showSeconds:false" style="width:324px"  /></p> 
		 
		 <p>
		 	<label>注册账号：</label><input id="txtcustName" class="easyui-validatebox"  readonly="readonly"   style="width:324px"  /><input id="txtcustId" type="hidden"/>
		 	<a id="btnSelectCustUser"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_search" plain="true">客户选择</a>
		 </p>	
		 <p><label>真实姓名：</label><input id="txtrealname" class="easyui-validatebox"  readonly="readonly"   style="width:324px"  /> </p>
		 <p><label>联系方式：</label><input id="txtcustPhone" class="easyui-validatebox"  readonly="readonly"   style="width:324px"  /> </p>
		 <p><label>电子邮件：</label><input id="txtcustEmail" class="easyui-validatebox"  readonly="readonly"    style="width:324px"  /> </p>
		 <p><label>充值备注：</label><textarea id="txtrechargeDesc" type="textarea" style="width:324px;height:40px" ></textarea></p> 
	</div>
	<div id="div_custuser" style="width:500px;height:300px; display:none">
		<div id="custUserGrid" fit="true">
		
		</div>
		<div id="tb_custUser">
			<label>客户姓名:</label><input id="search_custname" class="easyui-validatebox" style="width:120px" />
			<label>电话号码:</label><input id="search_phone" class="easyui-validatebox" style="width:120px" />
			<a id="btnSearchCustUser"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_search" plain="true">查询</a>
		</div>
	</div>
	 <div style="display:none">
	     <div id="div_printchargeBill">
	     <!--startprint-->
	     	<table style="text-align:center;font-size:14px;">
	     		<tr>
	     			<td colspan="3">
	     				<img alt="logo" src="resource/icon/menu/titleblack.png">
	     			</td>
	     		</tr>
	     		<tr>
	     			<td colspan="3">
	     				<label id="lbl_orgName"></label>
	     			</td>
	     		</tr>
	     		<tr>
	     			<td colspan="3">
	     				——————————————————
	     			</td>
	     		</tr>
	     		<tr>
	     			<td>业务日期：</td><td colspan="2"><label id="lbl_chargeTime"></label></td> 
	     		</tr>
	     		<tr>
	     			<td>业务类型：</td><td colspan="2">充值</td> 
	     		</tr>
	     		<tr>
	     			<td colspan="3">
	     				—————————————————
	     			</td>
	     		</tr>
	     		<tr>
	     			<td>客户</td><td>数量</td><td>金额(元)</td>
	     		</tr>
	     		<tr>
	     			<td><label id="lbl_customerName"></label></td><td>1</label></td><td><label id="lbl_chargeAmount"></td>
	     		</tr> 
	     		<tr>
	     			<td>合计：</td><td>1</td><td><label id="lbl_totalAmount"></label></td>
	     		</tr>
	     		<tr>
	     			<td colspan="3">
	     				—————————————————
	     			</td>
	     		</tr> 
	     		<tr>
	     			<td colspan="3">
	     				投诉电话：028-83139235
	     			</td>
	     		</tr>
	     		<tr>
	     			<td colspan="3">
	     				欢迎访问幸福享购官方商城了解更多产品信息！
	     			</td>
	     		</tr>
	     		<tr>
	     			<td colspan="3">
	     				网址：www.xfxg99.com
	     			</td>
	     		</tr>
	     		<tr>
	     			<td colspan="3">
	     				如无积分卡请办理，未带请报卡号。
	     			</td>
	     		</tr>
	     	</table>
	     	<!--endprint-->
	     </div>
     </div>
</body>
</html>
