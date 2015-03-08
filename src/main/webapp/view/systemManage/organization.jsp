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
    <div id="div_Organization" style="display:none;">
        <div style="width: 500px; height: 150px">
            <p style="margin-top: 0px; margin-bottom: 0px">
                <a id="SaveInfo" name="SaveInfo" href="javascript:void(0);" class="easyui-linkbutton"
                    iconcls="icon-save" plain="true">保存</a><a id="CancelInfo"
                        name="CancelInfo" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-no"
                        plain="true">取消</a>
            </p>
            <table style="font-size: 12px; height: 95%">
                <tr style="display: none">
                    <td>
                        <input id="txt_Id" type="hidden" />
                    </td>
                    <td>
                        <input id="txt_NodeLevel" type="hidden" />
                    </td>
                    <td>
                        <input id="txt_FullPath" type="hidden" />
                    </td>
                    <td>
                        <input id="txt_ParentId" type="hidden" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>上级组织机构:</label>
                    </td>
                    <td>
                        <input id="txt_ParentName" class="easyui-validatebox" type="text" readonly="true" data-options="required:false" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>组织机构名称:</label>
                    </td>
                    <td>
                        <input id="txt_Name" class="easyui-validatebox" type="text" data-options="required:true" />
                    </td>
                    <td>
                            <label>是否仓储管理:</label></td>
                        <td>
                            <input id="IsUsedCheck" type="checkbox" /></td>    
                </tr>
                <tr>
                    <td>地址:</td>
                    <td colspan="3">
                        <textarea id="txt_Address" cols="3" rows="2" style="width: 98%"></textarea>
                    </td>
                </tr>
            </table>
        </div>
    </div>
  </body>
</html>
