<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>系统参数管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src='<%=basePath%>script/base/sysparameter.js' 	type='text/javascript'></script>
  </head>
  
  
  <body>
    <div id="sysParamTb" >
        <div>
            <p> 
               <!-- <a id="AddParam" name="AddParam"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-add"
                    plain="true">新增</a>  --> 
                <a id="SaveParamInfo" name="SaveParamInfo" 
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-save"
                    plain="true">保存</a> 
            </p> 
        </div>
    </div>
    <div id="sysParamGrid"  style="margin:10px" ></div>
  </body>
</html>
