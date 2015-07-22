var m_goodssale_orgId; 
$(function () {
	var args = getUrlArgs();
	m_goodssale_orgId = args.orgId; 
    
    $("#cmtSaleDept").combotree({
    	url: 'organization/getTreeListByParentId.do?parentid='+m_goodssale_orgId
    });    
	$('#dgGoodsSaleList').datagrid({
		url:'sale/loadGoodsSaleList.do',
		queryParams : {
			'orgId' : m_goodssale_orgId
		},
        fitColumns: true,
        rownumbers: true,
        resizable: true,
        pagination: true,
        pageNumber: 1,
        pageSize: 10, 
        idField: 'id',
        singleSelect: true, 
        columns: [[
               { title: 'id', field: 'id', align: 'left', width: 5, hidden: true },
               { title: '单据号', field: 'serialNo', align: 'left', width: 150 },
               { title: '销售部门', field: 'orgName', align: 'left', width: 150},
               { title: '商品名称', field: 'goodsName', align: 'left', width:150},
               { title: '销售数量', field: 'goodsNumber', align: 'left', width: 150 }, 
               { title: '销售时间', field: 'saleTime', align: 'left', width: 150 }
        ]]
    }); 
});
function doSearch(){
	var orgId = $("#cmtSaleDept").combotree("getValue");
	if(orgId>0){
		$('#dgGoodsSaleList').datagrid('reload',{"orgId":orgId});
	}
}
 
 



