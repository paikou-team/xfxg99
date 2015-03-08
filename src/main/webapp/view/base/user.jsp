<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>用户管理</title>
	<script src='<%=basePath%>script/base/user.js' 	type='text/javascript'></script>
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
    <div id="UserGrid"  style="margin:10px" ></div>
    <div id="div_userProfile" class="displaynone" style="display:none;" style="width: 100%; height: 100%">
            <p style="margin-top: 0px; margin-bottom: 0px">
                <a id="SaveInfo" name="SaveInfo" href="javascript:void(0);" class="easyui-linkbutton"
                    iconcls="icon-save" plain="true">保存</a><a id="CancelInfo"
                        name="CancelInfo" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-no"
                        plain="true">取消</a>
            </p>
            <table style="width: 500px; height: 150px; font-size: 12px">
                    <tr style="display: none">
                        <td>
                            <input id="txt_Id" type="hidden" /></td>
                    </tr>
                    <tr>
                        <td>
                            <label>用户姓名:</label></td>
                        <td>
                            <input id="txt_Name" class="easyui-validatebox" type="text" data-options="required:true" /></td>
                        <td>
                            <label>初始密码:</label></td>
                        <td>
                            <input id="txt_Password" class="easyui-validatebox" data-options="required:true" type="password" /></td>
                    </tr>
                    <tr>
                        <td>
                            <label>组织机构:</label></td>
                        <td>
                            <input id="txt_OrganizationId" class="easyui-combobox" style="width: 160px" data-options="required:false" /></td>
                        <td>
                            <label>有效：</label></td>
                        <td>
                            <input id="IsUsedCheck" type="checkbox" /></td>    
                    </tr>
                    <tr>
                        <td>用户备注:</td>
                        <td colspan="3">
                            <textarea id="txt_Description" cols="5" rows="3" style="width: 95%"></textarea></td>
                    </tr>
                </table>
        </div>
  </body>
</html>
