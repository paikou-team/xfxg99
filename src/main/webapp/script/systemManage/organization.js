/**
 * 
 */
$(function () {
    $('#OrganizationTree').treegrid({
    	url: 'organization/getList.do',//对应Controller里面的控制器和方法；
        fitColumns: true,
        rownumbers: true,
        resizable: true,
        idField: 'FullPath',
        treeField: 'Name',
        toolbar:'#organizationtb',
        columns: [[
               { title: '组织机构名称', field: 'Name', align: 'left', width: 200 },
               { field: 'id', title: 'Id', width: 130, align: 'center', hidden: true },
               { title: '编码', field: 'Number', align: 'center', hidden: true },
               { title: '节点等级', field: 'NodeLevel', align: 'center', hidden: true },
               { title: '上级ID', field: 'ParentId', align: 'center', hidden: true },
               { title: '上级名称', field: 'ParentName', align: 'center', hidden: true },
               { title: '全路径', field: 'FullPath', align: 'center', hidden: true },
               { title: 'OrderNo', field: 'OrderNo', align: 'center', hidden: true },
               { title: 'LeaderId', field: 'LeaderId', align: 'center', hidden: true },
               { title: '地址', field: 'address', align: 'center', width: 100 }

        ]]
    });
    $("#AddOrganization").bind("click", Organization.AddOrganization);
    $("#EditOrganization").bind("click", Organization.EditOrganization);
    $("#DelOrganization").bind("click", Organization.DelOrganization);

    $("#SaveInfo").bind("click", SaveInfo);
    $("#CancelInfo").bind("click", CancelInfo);
});