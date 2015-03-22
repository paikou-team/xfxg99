<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<script src='<%=basePath%>script/index.js' 	type='text/javascript'></script>
    <title>派蔻营销管理系统</title>
    
</head> 
<body id="cc" class="easyui-layout"  style="width:100%;height:100%; overflow: hidden;" oncontextmenu=self.event.returnValue=false>
	<div region="north" style="background-color: #4974a4; height: 80px; overflow:hidden">
		<div style="float: left; padding-left: 15px;">
			<img height="75px" src="resource/icon/menu/banner_logo.png" />
		</div>
		<div>
			<div style="float: left;">
				<h1 style="color: orange;">O2O线下管理平台</h1>
				<h2>欣雨禾</h2>
			</div>
			<div style="float: right; padding-top: 54px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" onclick="onExit()">退出</a> 
			</div>
		</div> 
	</div>
	<div region="west" title="功能菜单" split=true style="width:220px;">
		<ul id="treeMenu"></ul>
	</div> 
	<div region=center title="业务" >
		<iframe id="ifrContent"  scrolling='no' frameborder='0'  style="width:100%;height:98%;" onLoad="iframeSize()">
		</iframe>
	</div> 
	<div region="south"  style="background-color: #4974a4; height: 30px; overflow:hidden">
		<table>
			<tr>
				<td>
					<label>部门</label>
				</td>
				<td>
					<label id='labOrgName'></label>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
