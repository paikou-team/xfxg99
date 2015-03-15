<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script src='<%=basePath%>script/base/organization.js' 	type='text/javascript'></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=15&ak=G2uYN5MHomvRQje6KY5HEt4e"></script>
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
        <div style="width: 100%; height: auto">
            <p style="margin-top: 0px; margin-bottom: 0px">
                <a id="SaveInfo" name="SaveInfo" href="javascript:void(0);" class="easyui-linkbutton"
                    iconcls="icon-save" plain="true">保存</a><a id="CancelInfo"
                        name="CancelInfo" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-no"
                        plain="true">取消</a>
            </p>
            <table style="font-size: 12px; height: 80%" >
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
                    <td>
                        <input id="txt_lng" type="hidden" />
                    </td>
                    <td>
                        <input id="txt_lat" type="hidden" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>上级组织机构:</label>
                    </td>
                    <td>
                        <input id="txt_ParentName" class="easyui-validatebox" type="text" readonly="true" data-options="required:false" />
                    </td>
                    <td>
                        <label>组织机构名称:</label>
                    </td>
                    <td>
                        <input id="txt_Name" class="easyui-validatebox" type="text" data-options="required:true" />
                    </td>
                    <td>
                        <label>是否仓储管理:</label></td>
                    <td style="width:30px">
                       <input id="IsStockCheck" type="checkbox" />
                    </td> 

                </tr>
                <tr>
       				<td colspan="5" class="noborder"></td>
    			</tr>
                <tr>
                	<td>
                		<label>联系电话:</label>
                	</td>
                	<td>
                        <input id="txt_Phone" class="easyui-validatebox" type="text" data-options="required:false" />
                    </td>

                    <td>
                    	<label>地址:</label>
                    </td>
                    <td colspan="2">
                        <input id="txt_Address"  style="width: 100%"></input>
                    </td>
                    <td>
        				<input type="button" value="查询" onclick="searchAddress()" style="font-size: 12px;" />
        			</td>
                </tr>
                <tr>
       				<td colspan="5" class="noborder"></td>
    			</tr>
            </table>
        	<div id="map" style="width: 680px; height: 320px"></div>
        	<script type="text/javascript"></script>
        	<div id="r-result style="font-size: 12px;">
			</div>
        </div>
    </div>
  </body>
</html>
