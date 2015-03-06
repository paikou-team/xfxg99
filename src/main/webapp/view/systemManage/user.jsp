<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>用户管理</title>
	<script src='<%=basePath%>script/systemManage/user.js' 	type='text/javascript'></script>
  </head>
  
  <body>
    <div id="UserTb" >
        <div>
            <p>
                <a id="AddUser" name="AddUser" href="javascript:void(0);" class="easyui-linkbutton"
                    iconcls="icon-add" plain="true">新增</a>
                <a id="EditUser" name="EditUser"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-edit"
                    plain="true">编辑</a>
                <a id="DelUser" name="DelUser"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-cancel"
                    plain="true">删除</a>
            </p>
        </div>
    </div>
    <div id="UserGrid"  style="margin:10px"  fit="true"></div>
  </body>
</html>
