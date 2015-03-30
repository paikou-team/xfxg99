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
	<script src='<%=basePath%>script/base/custuserList.js' 	type='text/javascript'></script>
  </head>
  
  <body>
     <div id="custUserTb" >
        <div>
            <p> 
                <a id="ShowCustUserInfo" name="ShowCustUserInfo"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_custview"
                    plain="true">查看</a> 
                <a id="btnBirthdayRecord" name="btnBirthdayRecord"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-large-smartart"
                    plain="true" >生日祝福</a>
                <a id="btnBirthdayLog" name="btnBirthdayLog"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-large-smartart"
                    plain="true">祝福记录</a>
                <!--  <a id="UnlockCustomer" name="UnlockCustomer"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-unlock"
                    plain="true">启用</a>
                <a id="LockCustomer" name="LockCustomer"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-lock"
                    plain="true">锁定</a> -->
            </p>
            <p id="tb_searchbox" style="margin-left:5px">
            	
            	  <label>手机号码:</label><input id="sch_phone" class="easyui-validatebox" style="width:120px" /> 
            	<label>客户姓名:</label><input id="sch_custname" class="easyui-validatebox" style="width:120px" />  
            	<a id="btnSearch"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_search" plain="true">查询</a> 
            </p>
        </div>
    </div>
    <div id="custUserGrid"  style="margin:10px" ></div>
    
  </body>
</html>
