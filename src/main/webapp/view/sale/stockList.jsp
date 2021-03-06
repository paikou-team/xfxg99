<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<script src='<%=basePath%>script/sale/stockList.js'	type='text/javascript'></script>
	<title>商品单据列表</title>
</head>

<body  >
	<div > 
		<p style="float: left; width:100%; height:10px">
			<a id="btnAddStockBill"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_add" plain="true" onclick="onStockBillAdd()">新增</a> 
			<a id="btnEditStockBill"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_edit" plain="true" onclick="onStockBillEdit()">查看</a> 
			<a id="btnConfirmStockBill"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_confirm" plain="true" onclick="onStockConfirm()">确认</a> 
			<a id="btnDelStockBill"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_delete" plain="true" onclick="onStockDel()">删除</a>
			  </p>
	</div>
	<br />
	<div style="width: 100%; float: left;">
	<div  style="margin-top:10px;"> 
		<ul>
			<li id="ligoodsSerial" style="float: left; list-style: none; margin: 0px; padding: 2px;">
				<label style="width: 70px;vertical-align:middle">商品条码:</label>
                <input id="goodsSerial" class="easyui-validatabox" style="width:180px" />
			</li>
			<li id="liStockInOrgId" style="float: left; list-style: none; margin: 0px; padding: 2px;">
				<label style="width: 70px;vertical-align:middle">入库部门:</label>
                <input id="cmbStockInDept" class="easyui-combobox" style="width:180px" />
			</li>
			<li id="liStockOutOrgId" style="float: left; list-style: none; margin: 0px; padding: 2px;">
				<label style="width: 70px">出库部门:</label>
                <input id="cmbStockOutDept" class="easyui-combobox" style="width:180px" />
			</li>
			<li style="float: left; list-style: none; margin: 0px; padding: 2px;">
				<label style="width: 70px">单据编号:</label>
                <input id="txtSerialNo" class="easyui-validatebox" style="width:180px" />
			</li>
			<li style="float: left; list-style: none; margin: 0px; padding: 2px;">
				<label style="width: 70px;vertical-align:middle">开始日期:</label>
                <input id="dteBeginTime" class="easyui-datebox" style="width:180px" />
			</li>
			<li style="float: left; list-style: none; margin: 0px; padding: 2px;">
				<label style="width: 70px">结束日期:</label>
                <input id="dteEndTime" class="easyui-datebox" style="width:180px" />
			</li>
			<li style="float: left; list-style: none; margin: 0px; padding: 2px;">
				<label style="width: 70px; margin-left: 24px;">状态:</label>
                <input id="cmbState" class="easyui-combobox" style="width:180px" />
			</li>
			
			<li style="float: left; list-style: none; margin: 0px; padding: 0px;">
            	<a id="btnStockSearch" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-search" plain="true" onclick="onStockSearch()">查询</a>
            </li>
			
			<li style="visibility:hidden;">
				<label>none</label>
			</li>
		</ul>
	</div>
	</div>
	<br />
	<div style="float: left; width: 100%;">
	<div id="dgStock"></div>
	</div>
</body>
</html>
