/**
 * 
 */
$(function () {
    $('#OrganizationTree').treegrid({
    	url: 'organization/getList.do',//��ӦController����Ŀ������ͷ�����
        fitColumns: true,
        rownumbers: true,
        resizable: true,
        idField: 'FullPath',
        treeField: 'Name',
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
    $("#AddOrganization").bind("click", Organization.AddOrganization);
    $("#EditOrganization").bind("click", Organization.EditOrganization);
    $("#DelOrganization").bind("click", Organization.DelOrganization);

    $("#SaveInfo").bind("click", SaveInfo);
    $("#CancelInfo").bind("click", CancelInfo);
});