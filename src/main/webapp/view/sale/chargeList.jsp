<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>客户充值列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src='<%=basePath%>script/sale/chargeList.js' 	type='text/javascript'></script>
  </head>
 
  <body>
     <div id="chargeTb" >
        <div>
            <p>
                <a id="AddCharge" name="AddCharge" href="javascript:void(0);" class="easyui-linkbutton"
                    iconcls="icon_xfxg_add" plain="true">新增充值</a>
                <a id="ConfirmCharge" name="ConfirmCharge"
                    href="javascript:void(0);" class="easyui-linkbutton displaynone" iconcls="icon_xfxg_confirm"
                    plain="true">充值确认</a>
                <a id="ShowChargeInfo" name="ShowChargeInfo"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_show"
                    plain="true">查看</a>
            </p>
            <p id="tb_searchbox" style="margin-left:5px">
            	<label>确认状态:</label><input id="sch_isconfirm" class="easyui-combobox" data-options="editable:false,valueField:'id',textField:'name',data:[{id: 0,name: '全部'},{id: 1,name: '已确认'},{id: 2,name: '未确认'}]" style="width:120px" />
            	<label>门店信息:</label><input id="sch_orgname" class="easyui-combotree" style="width:200px" />
            	<label>真实姓名:</label><input id="sch_custname" class="easyui-validatebox" style="width:120px" />
            	<label>开始时间:</label><input id="sch_startTime" class="easyui-datebox" style="width:120px" />
            	<label>结束时间:</label><input id="sch_endTime" class="easyui-datebox" style="width:120px" />
            	<!-- <label>确认人:</label><input id="sch_username" class="easyui-validatebox" style="width:120px" />  -->
            	<a id="btnSearch"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon_xfxg_search" plain="true">查询</a> 
            </p>
        </div>
    </div>
    <div id="chargeGrid"  style="margin:10px" ></div>
	<div id="statistic">
		<p><label>累计充值金额(元)：</label><label id="totalCharge"></label></p>
		<p id="p_hasC"><label>已确认充值金额(元)：</label><label id="hasConfirmCharge"></label></p>
		<p id="p_hasnotC"><label>未确认充值金额(元)：</label><label id="unConfirmCharge"></label></p>
	</div>
  </body>
</html>
