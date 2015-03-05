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
               { title: '��֯��������', field: 'Name', align: 'left', width: 200 },
               { field: 'id', title: 'Id', width: 130, align: 'center', hidden: true },
               { title: '����', field: 'Number', align: 'center', hidden: true },
               { title: '�ڵ�ȼ�', field: 'NodeLevel', align: 'center', hidden: true },
               { title: '�ϼ�ID', field: 'ParentId', align: 'center', hidden: true },
               { title: '�ϼ�����', field: 'ParentName', align: 'center', hidden: true },
               { title: 'ȫ·��', field: 'FullPath', align: 'center', hidden: true },
               { title: 'OrderNo', field: 'OrderNo', align: 'center', hidden: true },
               { title: 'LeaderId', field: 'LeaderId', align: 'center', hidden: true },
               { title: '��ַ', field: 'address', align: 'center', width: 100 }

        ]]
    });
    $("#AddOrganization").bind("click", Organization.AddOrganization);
    $("#EditOrganization").bind("click", Organization.EditOrganization);
    $("#DelOrganization").bind("click", Organization.DelOrganization);

    $("#SaveInfo").bind("click", SaveInfo);
    $("#CancelInfo").bind("click", CancelInfo);
});