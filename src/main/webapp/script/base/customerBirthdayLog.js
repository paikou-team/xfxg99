


var m_birthday_log_query={};


$(function () {
	$('#dgCBL').datagrid({
		url:'customer/loadBirthdayLog.do',
		queryParams : {
			'birthdayLogQuery' : JSON.stringify(m_birthday_log_query)
		},
        fitColumns: true,
        rownumbers: true,
        resizable: true,
        pagination: true,
        pageNumber: 1,
        pageSize: 20,
        nowrap: false,
        idField: 'id',
        singleSelect: true,
        onDblClickRow: onSelRow,
        columns: [[
               { title: 'id', field: 'id', align: 'left', width: 5, hidden: true },
               { title: '年', field: 'serialNo', align: 'left', width: 150 },
               { title: '生日', field: 'stockInOrgName', align: 'left', width: 120 },
               { title: '祝福日期', field: 'stockOutOrgName', align: 'left', width: 120 },
               { title: '祝福内容', field: 'billTime', align: 'center', width: 100 ,
            	   formatter:function(value,row,index){
            		   if(value > 0){
            			   return "已确认";
            		   }else{
            			   return "未确认";
            		   }
            	 }}
        ]]
    });
});


