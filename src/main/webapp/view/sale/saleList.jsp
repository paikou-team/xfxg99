<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<script src='<%=basePath%>script/sale/saleList.js'	type='text/javascript'></script>
	<title>商品单据列表</title>
</head>

<body  >
	<div > 
		<p style="float: left; width:100%; height:10px">
			<a id="btnAddSaleBill"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-add" plain="true" onclick="onSaleBillAdd()">新增</a> 
			<a id="btnEditSaleBill"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-edit" plain="true" onclick="onSaleBillEdit()">查看</a> 
			<a id="btnConfirmSaleBill"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-edit" plain="true" onclick="onSaleConfirm()">确认</a> 
			<a id="btnDelSaleBill"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-edit" plain="true" onclick="onSaleDel()">确认</a>
        </p>
	</div>
	<div style="margin-top:10px;">
		<table style="width:100%;height:20px;font-size: 12px">
			<tr>
				<td>
					<label style="width: 70px;vertical-align:middle">销售部门:</label>
				</td>
				<td>
					<input id=cmbSaleDept class="easyui-combobox" style="width:150px" />
				</td>
				<td>
					<label style="width: 70px">单据编号:</label>
				</td>
				<td  >
					<input id="txtSerialNo" class="easyui-validatebox" style="width:150px"/>
				</td>
				<td>
					<label style="width: 70px;vertical-align:middle">开始日期:</label>
				</td>
				<td >
					<input id="dteBeginTime" class="easyui-datebox"  style="width:150px"/>
				</td>
				<td>
					<label style="width: 70px">结束日期:</label>
				</td>
				<td>
					<input id="dteEndTime" class="easyui-datebox" style="width:150px" />
				</td>
				<td>
					<a id="btnSaleSearch" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-search" plain="true" onclick="onSaleSearch()">查询</a>
				</td>
			</tr>
		</table>
	</div>		
	<div id="dgSale"></div>
</body>
</html>
