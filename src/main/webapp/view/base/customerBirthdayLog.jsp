<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">
	<script src='<%=basePath%>script/base/customerBirthdayLog.js'	type='text/javascript'></script>
    
    <title>客户生日提示记录</title>
    
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
   <div > 
		<p style="float: left; width:100%; height:10px">
			<a id="btnCBLExit"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-edit" plain="true" onclick="onCBLExit()">确认</a>
        </p>
	</div>
	
	<table style="width: 100%; font-size: 12px">
		<tr>
			<td style="width: 80px; text-align: right">
				<label>姓名:</label>
            </td>
            <td style="width: 120px; text-align: right">
            	<input id="txtName" class="easyui-validatebox" data-options="required:false" disabled="disabled" />
            </td>
            <td style="width: 80px; text-align: right">
				<label>性别:</label>
            </td>
            <td style="width: 120px; text-align: right">
            	<input id="txtSex" class="easyui-validatebox" data-options="required:false" disabled="disabled" />
            </td>
		</tr>
		<tr>
			<td style="width: 80px; text-align: right">
				<label>生日:</label>
            </td>
            <td style="width: 120px; text-align: right">
            	<input id="txtBirthday" class="easyui-validatebox" data-options="required:false" disabled="disabled" />
            </td>
            <td style="width: 80px; text-align: right">
				<label>年龄:</label>
            </td>
            <td style="width: 120px; text-align: right">
            	<input id="txtAge" class="easyui-validatebox" data-options="required:false" disabled="disabled" />
            </td>
		</tr>
	</table>
	
	<div id="dgCBL" />
	
  </body>
</html>
