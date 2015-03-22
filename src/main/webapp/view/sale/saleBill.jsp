<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<script src='<%=basePath%>script/sale/salebill.js' 	type='text/javascript'></script>
    <title>出入库单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
<body style="overflow: hidden;">
    <div id="saleBillDiv">
        <p style="width: 100%; text-align: center; height: 18px;">
            <label id="billTypeName" style="font-size: 16pt; border-bottom: 1px solid">销售单</label>
        </p>
        <p style="margin-top: -1px; margin-bottom: 0px">
        <span id="tb_operationtb">
        	<a id="btnAddGoods" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_add" plain="true" onclick="onAddGoods()">添加</a>
        	<a id="btnDelGoods" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_delete" plain="true" onclick="onDelGoods()">删除</a>
            <a id="btnSaveBill" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_save" plain="true" onclick="onSaveSaleBill()">保存</a>
            <!--<a id="btnCheckBill" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_confirm" plain="true" onclick="onCheckStockBill()">确认</a>-->
         </span>
            <a id="btnExit" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_close" plain="true" onclick="onExit()">退出</a>
        </p>
        <div class="easyui-tabs" data-options="tabWidth:120" style="width: 100%; height: 340px; border-left: 1px solid #D4D4D4; border-right: 1px solid #D4D4D4">
            <div title="基本信息" data-options="tabWidth:115" style="padding: 3px">
                <table style="width: 100%; font-size: 12px">
                <tr>
                	<td><input id="txtcustId" type="hidden" /></td>
                	<td><input id="txtorgId" type="hidden" /></td>
                </tr>
                    <tr>
                        <td style="width: 60px; text-align: center">
                            <label>单据编号:</label>
                        </td>
                        <td style="width: 100px; text-align: left">
                            <input id="txtSerialNo" class="easyui-validatebox" data-options="required:false" disabled="disabled" />
                        </td>
                        <td style="width:60px; text-align: center">
                            <label>单据日期:</label>
                        </td>
                        <td style="width: 100px; text-align: left">
                            <input id="dteSaleTime" class="easyui-datetimebox" data-options="required:true,editable:false"   />
                        </td>                      
                    </tr>
                    <tr>
                    	<td style="width:60px; text-align: center">
                            <label id="lblSaleDetp">门店名称:</label>
                        </td>
                        <td style="width: 100px; text-align: left" >
                            <input id="cmbSaleDetp" class="easyui-combobox"  disabled="disabled"  data-options="required:true,editable:false"   />
                        </td>
                       	<td style="width:60px; text-align: center">
                            <label >客户名称:</label>
                        </td>
                        <td style="width: 100px; text-align: left">
                            <input id="textSaleCustomer" readonly="readonly" class="easyui-validatebox" data-options="required:false" />
                            <span id="viewBillInfo">
                            <a id="btnSelectCustomer"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-select" plain="true">客户选择</a>
                        	</span>
                        </td>
                        
                    </tr>
                    <tr>
                        <td style="width:60px; text-align: center">
                            <label>备注说明:</label>
                        </td>
                        <td colspan="3">
                            <textarea id="txtDescription" rows="2" style="width: 100%; font-size: 12px" cols="3"></textarea>
                       </td>

                    </tr>
                </table>

                <div>
                    <div id="dgSaleDetail"></div>
                </div>
            </div>
        </div>
        <table style="width: 100%; font-size: 12px">
            <tr>
                <td style="text-align: right">
                    <label>制单部门:</label>
                </td>
                <td style="text-align: right">
                    <input id="txtPreparerOrgName" class="easyui-validatebox" disabled="disabled" />
                </td>
                <td style="text-align: right">
                    <label>制单人:</label>
                </td>
                <td style="text-align: right">
                    <input id="txtPreparerName" class="easyui-validatebox" disabled="disabled" />
                </td>
                <td style="text-align: right">
                    <label>制单时间:</label>
                </td>
                <td style="text-align: right">
                    <input id="txtPrepareTime" class="easyui-validatebox" disabled="disabled" />
                </td>
            </tr>
        </table>
    </div>
    <div id="div_custuser" style="width:500px;height:300px; display:none">
		<div id="custUserGrid" fit="true">	
		</div>
		<div id="tb_custUser">
			<label>客户姓名:</label><input id="search_custname" class="easyui-validatebox" style="width:120px" />
			<a id="btnSearchCustUser"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_search" plain="true">查询</a>
		</div>
	</div>
</body>
</html>
