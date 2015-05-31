<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<script src='<%=basePath%>script/sale/cashsaleList.js'	type='text/javascript'></script>
	<title>商品单据列表</title>
</head>

<body  >
	<div > 
		<p style="float: left; width:100%; height:10px">
			<a id="btnAddSaleBill"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_add" plain="true" onclick="onSaleBillAdd()">新增</a> 
 			<a id="btnShowSaleBill"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_edit" plain="true" onclick="onSaleBillShow()">查看</a> 
   </p>
	</div>
	<div style="margin-top:10px;">
		<table style="width:100%;height:20px;font-size: 12px">
			<tr>
				<td>
					<label style="width: 70px;vertical-align:middle">提货状态:</label>
				</td>
				<td>
					<input id="cmbisdelivery" class="easyui-combobox" data-options="editable:false,valueField:'id',textField:'name',data:[{id: 0,name: '全部'},{id: 1,name: '未提货'},{id: 2,name: '已提货'}]" style="width:100px" />
				</td>
				<td>
					<label style="width: 70px;vertical-align:middle">销售部门:</label>
				</td>
				<td>
					<input id="cmtSaleDept" class="easyui-combotree" style="width:200px" />
				</td> 
				<td>
					<label style="width: 70px">单据编号:</label>
				</td>
				<td  >
					<input id="txtSerialNo" class="easyui-validatebox" style="width:100px"/>
				</td>
				<td>
					<label style="width: 70px;vertical-align:middle">开始日期:</label>
				</td>
				<td >
					<input id="dteBeginTime" class="easyui-datebox"  style="width:100px"/>
				</td>
				<td>
					<label style="width: 70px">结束日期:</label>
				</td>
				<td>
					<input id="dteEndTime" class="easyui-datebox" style="width:100px" />
				</td>
				<td>
					<a id="btnSaleSearch" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-search" plain="true" onclick="onSaleSearch()">查询</a>
				</td>
			</tr>
		</table>
	</div>		
	<div id="dgSale"></div>
	<div id="statistic">
		<p><label>累计销售金额(元)：</label><label id="totalPrice"></label></p>
	</div>
</body>
</html>
