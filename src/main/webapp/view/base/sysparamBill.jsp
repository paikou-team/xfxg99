<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<script src='<%=basePath%>script/base/sysparamBill.js'
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
		 <p id="tb_operation" style="padding:10px;border-bottom:1px solid black;">
		 	<a id="btnSaveParamInfo"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-save" plain="true">确定</a>
			<a id="btnCancelSave"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-cancel" plain="true">取消</a> 
		 </p>
		 <p style="padding:10px"><label>擦数类别：</label><input id="txtparamtype" class="easyui-combobox" data-options="editable:false,valueField:'id',textField:'name',data:[{id: '业务',name: '业务参数'},{id: '系统',name: '系统参数'}]" style="width:324px" /></p>
		 <p style="padding:10px"><label>参数名称：</label><input id="txtparamname" class="easyui-validatebox"  style="width:324px" /></p>
		 <p style="padding:10px"><label>参数Key：</label><input id="txtparamkey" class="easyui-validatebox" style="width:324px"  /></p> 
		 <p style="padding:10px"><label>参数值：</label><input id="txtparamvalue" type="text" class="easyui-validatebox" style="width:324px" /></p> 
		 <p style="padding:10px"><label>编辑类型：</label><input id="txtparamedittype" class="easyui-validatebox"  readonly="readonly"  style="width:324px"  /></p>		 
	</div>
  </body>
</html>
