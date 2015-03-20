<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<script src='<%=basePath%>script/index.js' 	type='text/javascript'></script>
    <title>派蔻营销管理系统</title>
    
</head> 
<body style="margin: 0; overflow: hidden;">
<div style="width: 100%; background-color: #4974a4; height: 80px;">
	<div style="float: left; padding-left: 15px;">
		<img height="75px" src="resource/icon/menu/banner_logo.png" />
	</div>
	<div>
		<div style="float: left;">
			<h1 style="color: white; margin: 0; padding-top: 20px;">幸福享购商城</h1>
			<h1 style="color: orange; margin: 0; padding-left: 27px;">O2O线下管理平台</h1>
		</div>
		<div style="float: right; padding-top: 54px;">
			<a href="javascript:void(0);" class="easyui-linkbutton"   style="color: black;">修改密码</a>
			<a href="javascript:void(0);" class="easyui-linkbutton"   onclick="onExit()" style="color: black;">退出</a> 
		</div>
	</div> 
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
