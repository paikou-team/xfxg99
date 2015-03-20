<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script src='<%=basePath%>script/sale/goodsList.js' 	type='text/javascript'></script>
    <title>商品查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<p style="margin-top: -1px; margin-bottom: 0px">
	<a id="btnExit" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_close" plain="true" onclick="exit()">退出</a>
	</p>
	<input class="easyui-searchbox" data-options="prompt:'输入商品名称',searcher:doSearch" style="width:300px"></input>
	<div style="height:10px"></div>
    <div>
		<div id="dgGoods"  style="margin:10px"></div>
	</div>
  </body>
</html>
