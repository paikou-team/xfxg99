<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<script src='<%=basePath%>script/index.js' 	type='text/javascript'></script>
    <title>派蔻营销管理系统</title>
</head>
<body>
<div>
	<h2>欣雨禾</h2>
</div>
<div id="cc" class="easyui-layout" style="width:100%;height:100%;" oncontextmenu=self.event.returnValue=false>
    <div data-options="region:'west',title:'功能菜单',split:true" style="width:220px;">
    	<ul id="treeMenu"></ul>
    </div>    
    <div data-options="region:'center',title:' 业务'  " >
		<iframe id="ifrContent"  scrolling='no' frameborder='0'  style="width:100%;height:98%;" onLoad="iframeSize()">
    	</iframe>
    </div> 
</div>
</body>
</html>
