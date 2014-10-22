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
        	<a id="btnAddGoods" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_add" plain="true" onclick="onAddGoods()">添加商品</a>
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
                        <td style="width: 60px; text-align: right">
                            <label>单据编号:</label>
                        </td>
                        <td style="width: 100px; text-align: left">
                            <input id="txtSerialNo" class="easyui-validatebox" data-options="required:false" disabled="disabled" />
                        </td>
                        <td style="width:70px; text-align: right">
                            <label>单据日期:</label>
                        </td>
                        <td style="width: 100px; text-align: left">
                            <input id="dteSaleTime" class="easyui-datebox" data-options="required:true,editable:false"   />
                           
                        </td>         
                    </tr>
                    <tr>
                    	<td style="width:60px; text-align: right">
                            <label id="lblSaleDetp">门店名称:</label>
                        </td>
                        <td style="width: 100px; text-align: left" >
                            <input id="cmbSaleDetp" class="easyui-combobox"  disabled="disabled"  data-options="required:true,editable:false"   />
                        </td>
                       	<td style="width:70px; text-align: right">
                            <label >客户名称:</label>
                        </td>
                        <td style="width: 120px; text-align: left">
                            <input id="textSaleCustomer" readonly="readonly" class="easyui-validatebox" data-options="required:false" />
                            <span id="viewBillInfo">
                            <a id="btnSelectCustomer"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-select" plain="true">客户选择</a>
                        	</span>
                        </td>
                        
                    </tr>
                    
                    <tr>
                    	<td style="width:60px; text-align: right">
                    		<label >手机号码:</label>
                    	</td>
                    	<td style="width: 100px; text-align: left">
                            <input id="txtMobile" readonly="readonly" class="easyui-validatebox" data-options="required:false" />
                            
                        </td>
                        <td style="width:70px; text-align: right">
                    		<label id="labelCode" >输入验证码:</label>
                    	</td>
                    	<td style="width: 120px; text-align: left">
                            <input id="txtVerifCode"  class="easyui-validatebox" data-options="required:false" />
                            <a id="btnGetVerifCode" sytle="width:60px" href="javascript:void(0);" iconcls="icon_xfxg_message" class="easyui-linkbutton"  onclick="onGetVerifCode()">获取验证码</input>
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
			<label>电话号码:</label><input id="search_phone" class="easyui-validatebox" style="width:120px" />
			<a id="btnSearchCustUser"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_search" plain="true">查询</a>
		</div>
	</div>
	 <div style="display:none">
	     <div id="div_printSaleBill">
	     <!--startprint-->
	     	<table style="text-align:center;font-size:14px;width:120px;">
	     		<tr>
	     			<td colspan="3">
	     				<img alt="logo" src="resource/icon/menu/titleblack.png">
	     			</td>
	     		</tr>
	     		<tr>
	     			<td colspan="3">
	     				<label id="lbl_orgName"></label>
	     			</td>
	     		</tr>
	     		<tr>
	     			<td colspan="3">
	     				—————————————————
	     			</td>
	     		</tr>
	     		<tr>
	     			<td>客户名称：</td><td colspan="2"><label id="lbl_customerName"></label></td> 
	     		</tr>
	     		<tr>
	     			<td>业务类型：</td><td colspan="2">商品消费</td> 
	     		</tr>
	     		<tr>
	     			<td>业务日期：</td><td colspan="2"><label id="lbl_saleTime"></label></td> 
	     		</tr>
	     		<tr>
	     			<td>单据编号：</td><td colspan="2"><label id="lbl_saleSerialNo"></label></td> 
	     		</tr>
	     		<tr>
	     			<td colspan="3">
	     				—————————————————
	     			</td>
	     		</tr>
	     		<tr>
	     			<td>商品</td><td>数量</td><td>金额(元)</td>
	     		</tr>
	     		<tr> 
	     			<td colspan="3">
	     				<table id="tbl_productList" style="width:100%;"> 
	     				</table> 
	     			</td>
	     		</tr> 
	     		<tr>
	     			<td>合计：</td><td><label id="lbl_totalcount"></label></td><td><label id="lbl_totalAmount"></label></td>
	     		</tr>
	     		<tr>
	     			<td colspan="3">
	     				—————————————————
	     			</td>
	     		</tr> 
	     		<tr>
	     			<td colspan="3">
	     				投诉电话：028-83139235
	     			</td>
	     		</tr>
	     		<tr>
	     			<td colspan="3">
	     				欢迎访问幸福享购官方商城了解更多产品信息！
	     			</td>
	     		</tr>
	     		<tr>
	     			<td colspan="3">
	     				网址：www.xfxg99.com
	     			</td>
	     		</tr>
	     		<tr>
	     			<td colspan="3">
	     				如无积分卡请办理，未带请报卡号。
	     			</td>
	     		</tr>
	     	</table>
	     	<!--endprint-->
	     </div>
     </div>
</body>
</html>
