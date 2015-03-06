<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">

	<link rel="stylesheet" type="text/css" href="asset/css/images/dateStyle.css"/>
	<script src='<%=basePath%>script/sale/stockList.js'	type='text/javascript'></script>

	<title>商品单据列表</title>
</head>

<body  oncontextmenu=self.event.returnValue=false>
<div id="div_stockBillDataGrid">
        <!--单据列表-->
        <div id="dgStock">
        </div>
    </div>
        <!--单据列表工具条-->
    <div id="stockBillToolbar" >
        <div style="height:20px">
            <p style="float: left; width:100%; height:10px">
                <a id="btnAddStockBill"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-add" plain="true" onclick="addStockBillAction()">新增</a> 
                <a id="btnEditStockBill"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-edit" plain="true" onclick="editStockBillAction()">查看</a> 
            </p>
        </div>
        
    </div>
	
   
</body>
</html>
