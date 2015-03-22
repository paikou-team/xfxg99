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
		<div style="float: right; padding-top: 54px; padding-right: 15px;">
			<a href="javascript:void(0);" class="easyui-linkbutton"  plain="true" style="color: black;" onclick="onChangePwd()">修改密码</a>
			<a href="javascript:void(0);" class="easyui-linkbutton"  plain="true"  onclick="onExit()" style="color: black;">退出</a> 
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

<!-- 修改密码对话框 -->

<div id="div_changePwd" style="display:none">
	<form action="index/changePwdAction.do" method="post">
		<table style="margin: 20px;">
			<tr style="height: 40px;">
				<td align="right" style="font-size: 12px;">旧密码：</td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr style="height: 40px;">
				<td align="right" style="font-size: 12px;">新密码：</td>
				<td><input type="password" name="newPassword" /></td>
			</tr>
			<tr style="height: 40px;">
				<td align="right" style="font-size: 12px;">确认密码：</td>
				<td><input type="password" name="TooNewPassword" /></td>
			</tr>
			<tr style="height: 40px;">
				<td align="center" style="font-size: 12px;"><input type="reset" class="easyui-linkbutton" value="重置" /></td>
				<td align="center" style="font-size: 12px;"><input type="submit" class="easyui-linkbutton" value="确定" /></td>
			</tr>
		</table>
	</form>

</div>
</body>
</html>
