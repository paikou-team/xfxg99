<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script src='<%=basePath%>script/systemManage/organization.js' 	type='text/javascript'></script>
    <title>组织结构</title>

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
    <div id="OrganizationTree"  style="margin:10px"></div>
  </body>
</html>
