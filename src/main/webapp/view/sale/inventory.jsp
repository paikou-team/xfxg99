<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<script src='<%=basePath%>script/sale/inventory.js'	type='text/javascript'></script>
	<title>库存查询</title>
</head>

<body  >
<div > 
		<p style="float: left; width:100%; height:10px">
			 
			<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_export" plain="true" onclick="onStockBillExport()">导出Excel</a> 
        </p>
	</div>
	<br />
	<div  style="margin-top:10px;">
		<ul>
			<li id="liStockOutOrgId" style="float: left; list-style: none; margin: 0px; padding: 2px;">
				<label style="width: 70px">部门:</label>
				<input id="cmbDept" class="easyui-combotree" style="width:200px" />
                <!--<input id="cmbDept" class="easyui-combobox" style="width:180px" />-->
			</li>
			<li style="float: left; list-style: none; margin: 0px; padding: 2px;">
				<label style="width: 70px">商品名称:</label>
                <input id="txtGoodsName" class="easyui-validatebox" style="width:180px" />
			</li>
			
			
			<li style="float: left; list-style: none; margin: 0px; padding: 0px;">
            	<a id="btnStockSearch" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_search" plain="true" onclick="onStockSearch()">查询</a>
            </li>
			
			<li style="visibility:hidden;">
				<label>none</label>
			</li>
		</ul>
	</div>
	<br />
	<div id="dgInventory"></div>
</body>
</html>
