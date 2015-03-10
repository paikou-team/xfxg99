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
                
               	<input class="easyui-searchbox" data-options="prompt:'输入用户名称',searcher:doSearch" style="width:300px"></input>
				<div style="height:10px"></div>
    			<div>
					<div id="dgUsers"  style="margin:10px"></div>
				</div>
                  
            </p>
        </div>
    </div>
    <div id="UserGrid"  style="margin:10px" ></div>
    <div id="div_userProfile" style="display:none;"  style="width:500px;" style="height:400px;">
		<p style="margin-top: 0px; margin-bottom: 5px">
                <a id="SaveInfo" name="SaveInfo" href="javascript:void(0);" class="easyui-linkbutton"
                    iconcls="icon-save" plain="true">保存</a><a id="CancelInfo"
                        name="CancelInfo" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-no"
                        plain="true">取消</a>
            </p>
            <div id="tabs" class="easyui-tabs" style="width:500px;height: 380px;" >
            	<div id="tab1" title="基础资料" style="padding: 10px">
            		<table style="width: 480px; height: 40%; font-size: 12px">
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
                            <input id="txt_OrganizationId" class="easyui-combotree" style="width: 160px" data-options="required:false" /></td>
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
            	<div id="tab2" title="权限设置" style="padding: 10px">
            		<table style="width: 480px; height: 30px; font-size: 12px">
            			<td>
                            <label>是否查看所有数据：</label></td>
                        <td>
                            <input id="IsAllDataPermissionCheck" type="checkbox" /></td>   
            		</table>
                	<div id="functionTree" style="width: 480px; height:200px; font-size: 12px"></div>
            	</div>
            </div>
     </div>
  </body>
</html>
