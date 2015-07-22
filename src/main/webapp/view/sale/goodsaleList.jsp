<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<script src='<%=basePath%>script/sale/goodsSaleList.js'	type='text/javascript'></script>
	<title>商品单据列表</title>
</head>

<body  >
	<div style="margin-top:10px;">
		<table style="height:20px;font-size: 12px">
			<tr> 
				<td>
					<label>销售部门：</label>
				</td>  
				<td>
					<input id="cmtSaleDept" class="easyui-combotree" style="width:200px" />
				</td>  
				<td>
					<a id="btnSaleSearch" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-search" plain="true" onclick="doSearch()">查询</a>
				</td>
			</tr>
		</table>
	</div>		
	<div id="dgGoodsSaleList"></div> 
</body>
</html>
