<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>客户资料列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src='<%=basePath%>script/base/birthdayInfoList.js' 	type='text/javascript'></script>
  </head>
  
  <body>
     <div id="custUserTb" >
        <div>
            <p> 
                <a id="ShowCustUserInfo" name="ShowCustUserInfo"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-large-smartart"
                    plain="true">客户资料</a>
                <a id="btnBirthdayRecord" name="btnBirthdayRecord"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-large-smartart"
                    plain="true" >生日祝福</a>
            </p>
            <div  style="margin-top:10px;">
		<ul>
			<li id="liStockInOrgId" style="float: left; list-style: none; margin: 0px; padding: 2px;">
				<label style="width: 70px;vertical-align:middle">门店:</label>
                <input id="txtShopName" class="easyui-validatebox" style="width:180px" />
			</li>
			<li id="liStockOutOrgId" style="float: left; list-style: none; margin: 0px; padding: 2px;">
				<label style="width: 70px">客户:</label>
                <input id=txtCustName class="easyui-validatebox" style="width:180px" />
			</li>
			<li style="float: left; list-style: none; margin: 0px; padding: 0px;">
            	<a id="btnBirthdayInfoSearch" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-search" plain="true" onclick="onBirthdayInfoSearch()">查询</a>
            </li>
			<li style="visibility:hidden;">
				<label>none</label>
			</li>
		</ul>
	</div>
        </div>
    </div>
    <div id="dgBirthdayInfo"  style="margin:10px" ></div>
  </body>
</html>
