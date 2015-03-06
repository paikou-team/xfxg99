<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>组织结构</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <div id="OrganizationTb">
        <div>
            <p>
                <a id="AddOrganization" name="AddOrganization" href="javascript:void(0);" class="easyui-linkbutton"
                    iconcls="icon-add" plain="true">新增</a>
                <a id="EditOrganization" name="EditOrganization"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-edit"
                    plain="true">编辑</a>
                <a id="DelOrganization" name="DelOrganization"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-cancel"
                    plain="true">删除</a>
            </p>
        </div>
    </div>
    <div id="OrganizationTree" fit="true"></div>
  </body>
</html>
