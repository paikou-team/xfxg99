<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<script src='<%=basePath%>script/base/customerBill.js'
	type='text/javascript'></script> 
<title>客户资料</title>

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
		 <p id="tb_operation" style="padding:2px;border-bottom:1px solid black;">
		 	<!-- <a id="btnSaveCustUserInfo"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_save" plain="true">确定</a> -->
			<a id="btnCancelSave"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_close" plain="true">关闭</a> 
		 </p>
		 
		 <p><label>注册账号：</label><input id="txtcustname" class="easyui-validatebox" readonly="readonly" style="width:324px" /><input id="txtid" type="hidden"/></p>
		 <p><label>真实姓名：</label><input id="txtrealname" type="text" class="easyui-validatebox" readonly="readonly"   style="width:324px" /></p>
		 <p><label>客户性别：</label><input id="txtcustsex" class="easyui-combobox" data-options="editable:false,valueField:'id',textField:'name',data:[{id: 0,name: '女'},{id: 1,name: '男'},{id: 2,name: '女'}]" style="width:324px"  disabled="disabled" /></p>
		 <p><label>出生日期：</label><input id="txtcustbirth" class="easyui-datebox" data-options="required:false,editable:false" style="width:324px"   disabled="disabled" /></p> 
		 <p><label>联系方式：</label><input id="txtcustphone" type="text" class="easyui-validatebox" readonly="readonly"   style="width:324px" /></p> 
		 <p><label>电子邮件：</label><input id="txtcustemail" class="easyui-validatebox"  readonly="readonly"  style="width:324px"  /></p> 
		  	
		 <p><label>推荐客户：</label><input id="txtrecuser" class="easyui-validatebox"  readonly="readonly"   style="width:324px"  /> </p>
		 <p><label>门店信息：</label><input id="txtorgname" class="easyui-validatebox"  readonly="readonly"    style="width:324px"  /> </p> 
	</div>
  </body>
</html>
