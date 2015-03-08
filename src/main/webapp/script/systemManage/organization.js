/**
 * 
 */
$(function () {
    $('#OrganizationTree').treegrid({
    	url: 'organization/getSortList.do',
        fitColumns: true,
        rownumbers: true,
        resizable: true,
        idField: 'path',
        treeField: 'name',
        toolbar:'#organizationtb',
        columns: [[
               { title: '组织机构名称', field: 'name', align: 'left', width: 200 },
               { field: 'id', title: 'Id', width: 130, align: 'center', hidden: true },
               { title: 'level', field: 'level', align: 'center', hidden: true },
               { title: 'parentId', field: 'parentId', align: 'center', hidden: true },
               { title: 'ParentName', field: 'ParentName', align: 'center', hidden: true },
               { title: 'path', field: 'path', align: 'center', hidden: true },
               { title: 'OrderNo', field: 'OrderNo', align: 'center', hidden: true },
               { title: '地址', field: 'address', align: 'center', width: 100 }

        ]]
    });
    
//    $("#OrganizationTree").treegrid({
//    	url : 'organization/getParentIdItems.do?id=0',//首次查询路径
//    	//queryParams:{"bomid":"${bomid}"},//首次查询参数		
//    	columns : [ [
//    	             { title: '组织机构名称', field: 'name', align: 'left', width: 200 },
//    	             { field: 'id', title: 'Id', width: 130, align: 'center', hidden: true },
//    	             { title: 'level', field: 'level', align: 'center', hidden: true },
//    	             { title: 'parentId', field: 'parentId', align: 'center', hidden: true },
//    	             { title: 'ParentName', field: 'ParentName', align: 'center', hidden: true },
//    	             { title: 'path', field: 'path', align: 'center', hidden: true },
//    	             { title: 'OrderNo', field: 'OrderNo', align: 'center', hidden: true },
//    	             { title: '地址', field: 'address', align: 'center', width: 100 }
//    	           ] ],
//    	onBeforeExpand:function(row){
//
//    		var url = "organization/getParentIdItems.do?id="+row.treeid;
//    		$("#OrganizationTree").treegrid("options").url = url;
//    		return true;	
//    	}
//    });
//    $("#AddOrganization").bind("click", Organization.AddOrganization);
//    $("#EditOrganization").bind("click", Organization.EditOrganization);
//    $("#DelOrganization").bind("click", Organization.DelOrganization);
//
//    $("#SaveInfo").bind("click", SaveInfo);
//    $("#CancelInfo").bind("click", CancelInfo);
});