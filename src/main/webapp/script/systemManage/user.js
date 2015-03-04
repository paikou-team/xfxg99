/**
 * 
 */
$(function () {
    //��ʼ�����ݱ��UserGrid
    $('#UserGrid').datagrid({
        url: '/SystemManage/GetUserList',//��ӦController����Ŀ������ͷ�����
        //title: '�û�����',
        fitColumns: true,
        idField: 'Id',
        pagination: true,
        pageNumber: 1,
        pageSize: 20,
        nowrap: false,
        toolbar: '#usertb',
        singleSelect: true,
        columns: [[
                    { field: 'IsUsed', title: '��Ч', width: 50, align: 'center' ,formatter: imgcheckbox },
                    //{ field: 'IsAllDataPermission', title: '����Ȩ��', width: 50, align: 'center' ,formatter: imgcheckbox, hidden:true },
                    { field: 'Name', title: '�û�����', width: 100, align: 'center' },
                    { field: 'Password', title: '��¼����', width: 100, align: 'center',hidden: true  },
                    { field: 'Number', title: '�û�����', width: 100, align: 'center' },
                    { field: 'OrganizationId', title: '���ű��',  hidden: true },
                    { field: 'OrganizationName', title: '����', width: 100, align: 'center' },
                    { field: 'Description', title: '��ע', width: 60, align: 'center' },
                    { field: 'Id', title: 'Id', width: 130, align: 'center', hidden: true }
        ]]
    }); //��ʼ�����ݱ��Usergrid
    $("#AddUser").bind("click", UserManage.AddUser);
    $("#EditUser").bind("click", UserManage.EditUser);
    $("#DelUser").bind("click", UserManage.DelUser);
    $("#SearchUser").bind("click", UserManage.SearchUser);
    $("#SaveInfo").bind("click", SaveInfo);
    $("#CancelInfo").bind("click", CancelInfo);  
});
