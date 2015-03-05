<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<script src='<%=basePath%>script/sale/stockbill.js' 	type='text/javascript'></script>
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
    <div id="stockBillDiv">
        <p style="width: 100%; text-align: center; height: 18px;">
            <label id="billTypeName" style="font-size: 16pt; border-bottom: 1px solid">商品入库单</label>
        </p>
        <p style="margin-top: -1px; margin-bottom: 0px">
            <a id="btnSaveBill" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-save" plain="true" onclick="saveStockBillAction()">保存</a>
            <a id="btnCheck" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-split" plain="true" onclick="checkBillAction()">确认</a>
            <a id="btnExit" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-no" plain="true" onclick="exit()">退出</a>
        </p>
        <div class="easyui-tabs" data-options="tabWidth:120" style="width: 100%; height: 340px; border-left: 1px solid #D4D4D4; border-right: 1px solid #D4D4D4">
            <div title="基本信息" data-options="tabWidth:115" style="padding: 3px">
                <table style="width: 100%">
                    <tr>
                        <td style="width: 120px; text-align: right">
                            <label>单据编号:</label>
                        </td>
                        <td style="width: 120px; text-align: right">
                            <input id="txtSerialNo" class="easyui-validatebox" data-options="required:false" disabled="disabled" />
                        </td>
                        <td style="text-align: right">
                            <label id="lblStockInDetp">入库部门:</label>
                        </td>
                        <td style="text-align: right">
                            <input id="txtStockInDetp" class=" easyui-combotree" data-options="required:false" />
                        </td>
                       <td style="text-align: right">
                            <label id=""lblStockOutDetp"">出库部门:</label>
                        </td>
                        <td style="text-align: right">
                            <input id="txtStockOutDetp" class=" easyui-combotree" data-options="required:false" />
                        </td>
                    </tr>
                    
                    <tr>
                        <td style="text-align:right">
                            <label>入库时间:</label>
                        </td>
                        <td style="text-align:right">
                            <input id="dteStockTime" class="easyui-datetimebox"   />
                        </td>
                    </tr>
                    
                    <tr>
                        <td style="text-align: right">
                            <label>备注说明:</label>
                        </td>
                        <td colspan="5">
                            <textarea id="txtDescription" rows="2" style="width: 99%" cols="5"></textarea>
                       </td>

                    </tr>
                </table>

                <div>
                    <div id="dgStockDetail"></div>
                </div>
            </div>
        </div>
        <table style="width: 100%; font-size: 12px">
            <tr>
                <td style="text-align: right">
                    <label>制单部门:</label>
                </td>
                <td style="text-align: right">
                    <input id="txtDepartment" class="easyui-validatebox" disabled="disabled" />
                </td>
                <td style="text-align: right">
                    <label>制单人:</label>
                </td>
                <td style="text-align: right">
                    <input id="txtPreparer" class="easyui-validatebox" disabled="disabled" />
                </td>
                <td style="text-align: right">
                    <label>制单时间:</label>
                </td>
                <td style="text-align: right">
                    <input id="txtBillTime" class="easyui-validatebox" disabled="disabled" />
                </td>
            </tr>
            <tr>
                <td style="text-align: right">
                    <label>确认部门:</label>
                </td>
                <td style="text-align: right">
                    <input id="txtCheckDept" class="easyui-validatebox" disabled="disabled" />
                </td>
                <td style="text-align: right">
                    <label>确认人:</label>
                </td>
                <td style="text-align: right">
                    <input id="txtChecker" class="easyui-validatebox" disabled="disabled" />
                </td>
                <td style="text-align: right">
                    <label>确认时间:</label>
                </td>
                <td style="text-align: right">
                    <input id="txtCheckTime" class="easyui-validatebox" disabled="disabled" />
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
