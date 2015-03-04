/**
 * 
 */
$(function () {
    //初始化数据表格UserGrid
    $('#UserGrid').datagrid({
        url: '/SystemManage/GetUserList',//对应Controller里面的控制器和方法；
        //title: '用户资料',
        fitColumns: true,
        idField: 'Id',
        pagination: true,
        pageNumber: 1,
        pageSize: 20,
        nowrap: false,
        toolbar: '#usertb',
        singleSelect: true,
        columns: [[
                    { field: 'IsUsed', title: '有效', width: 50, align: 'center' ,formatter: imgcheckbox },
                    //{ field: 'IsAllDataPermission', title: '所有权限', width: 50, align: 'center' ,formatter: imgcheckbox, hidden:true },
                    { field: 'Name', title: '用户名称', width: 100, align: 'center' },
                    { field: 'Password', title: '登录密码', width: 100, align: 'center',hidden: true  },
                    { field: 'Number', title: '用户编码', width: 100, align: 'center' },
                    { field: 'OrganizationId', title: '部门编号',  hidden: true },
                    { field: 'OrganizationName', title: '部门', width: 100, align: 'center' },
                    { field: 'Description', title: '备注', width: 60, align: 'center' },
                    { field: 'Id', title: 'Id', width: 130, align: 'center', hidden: true }
        ]]
    }); //初始化数据表格Usergrid
    $("#AddUser").bind("click", UserManage.AddUser);
    $("#EditUser").bind("click", UserManage.EditUser);
    $("#DelUser").bind("click", UserManage.DelUser);
    $("#SearchUser").bind("click", UserManage.SearchUser);
    $("#SaveInfo").bind("click", SaveInfo);
    $("#CancelInfo").bind("click", CancelInfo);  
});
