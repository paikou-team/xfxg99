/**
 * 
 */
$(function () {
    //��ʼ�����ݱ��UserGrid
    $('#UserGrid').datagrid({
        url: 'user/getList.do',//��ӦController����Ŀ������ͷ�����
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
                    { field: 'isused', title: '��Ч', width: 50, align: 'center' ,formatter: imgcheckbox },
                    //{ field: 'IsAllDataPermission', title: '����Ȩ��', width: 50, align: 'center' ,formatter: imgcheckbox, hidden:true },
                    { field: 'name', title: '�û�����', width: 100, align: 'center' },
                    { field: 'password', title: '��¼����', width: 100, align: 'center',hidden: true  },
                    { field: 'orgId', title: '���ű��',  hidden: true },
                    { field: 'orgName', title: '����', width: 100, align: 'center' },
                    { field: 'description', title: '��ע', width: 60, align: 'center' },
                    { field: 'id', title: 'Id', width: 130, align: 'center', hidden: true }
        ]]
    }); //��ʼ�����ݱ��Usergrid
    $("#AddUser").bind("click", UserManage.AddUser);
    $("#EditUser").bind("click", UserManage.EditUser);
    $("#DelUser").bind("click", UserManage.DelUser);
    $("#SearchUser").bind("click", UserManage.SearchUser);
    $("#SaveInfo").bind("click", SaveInfo);
    $("#CancelInfo").bind("click", CancelInfo);  
});
