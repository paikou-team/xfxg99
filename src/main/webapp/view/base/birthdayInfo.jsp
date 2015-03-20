<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">
	<script src='<%=basePath%>script/base/birthdayInfo.js'	type='text/javascript'></script>
    
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
			<a id="btnCBLPrev"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_prev" plain="true" onclick="onCBLPrev()">上一年</a>
			<a id="btnCBLNext"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_next" plain="true" onclick="onCBLNext()">下一年</a>
			<a id="btnCBLSave"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-save" plain="true" onclick="onCBLSave()">保存</a>
			<a id="btnCBLExit"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_close" plain="true" onclick="onCBLExit()">退出</a>
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
		<tr>
			<td style="width: 80px; text-align: right">
				<label>年份:</label>
			</td>
			<td style="width: 120px; text-align: right">
            	<input id="txtYear" class="easyui-validatebox" data-options="required:false" disabled="disabled" />
            </td>
		</tr>
		<tr style="height:50px">
			<td style="width: 80px; text-align: right">
				<label>祝福方式:</label>
            </td>
			<td colspan="3">
 				<textarea id="txtDescription" rows="2" style="width: 100%; ;height:100%;font-size: 12px" cols="3"></textarea>
			</td>
		<tr>
		
		
	</table>
	
	<div id="dgCBL" />
	
  </body>
</html>
