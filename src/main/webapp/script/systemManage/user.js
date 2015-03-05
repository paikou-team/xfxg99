/**
 * 
 */
$(function () {
    //初始化数据表格UserGrid
    $('#UserGrid').datagrid({
        url: 'user/getList.do',//对应Controller里面的控制器和方法；
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
                    { field: 'isused', title: '有效', width: 50, align: 'center' ,formatter: imgcheckbox },
                    //{ field: 'IsAllDataPermission', title: '所有权限', width: 50, align: 'center' ,formatter: imgcheckbox, hidden:true },
                    { field: 'name', title: '用户名称', width: 100, align: 'center' },
                    { field: 'password', title: '登录密码', width: 100, align: 'center',hidden: true  },
                    { field: 'orgId', title: '部门编号',  hidden: true },
                    { field: 'orgName', title: '部门', width: 100, align: 'center' },
                    { field: 'description', title: '备注', width: 60, align: 'center' },
                    { field: 'id', title: 'Id', width: 130, align: 'center', hidden: true }
        ]]
    }); //初始化数据表格Usergrid
    $("#AddUser").bind("click", UserManage.AddUser);
    $("#EditUser").bind("click", UserManage.EditUser);
    $("#DelUser").bind("click", UserManage.DelUser);
    $("#SearchUser").bind("click", UserManage.SearchUser);
    $("#SaveInfo").bind("click", SaveInfo);
    $("#CancelInfo").bind("click", CancelInfo);  
});
