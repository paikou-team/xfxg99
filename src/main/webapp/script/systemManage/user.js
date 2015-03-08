
var m_user_query={userName:null};
var mSelectedUser;
$(function () {
	var args = getUrlArgs();
	
	$('#UserGrid').datagrid({
		url:'user/getList.do',
		queryParams : {
			'userQuery' : JSON.stringify(m_user_query)
		},
        fitColumns: true,
        rownumbers: true,
        resizable: true,
        pagination: true,
        pageNumber: 1,
        pageSize: 20,
        nowrap: false,
        idField: 'Id',
        singleSelect: true,
        onDblClickRow: onSelRow,
        toolbar: "#UserTb",
        columns: [[
               { title: 'Id', field: 'id', align: 'left', width: 5, hidden: true },
               { title: '有效', field: 'isUsed', width: 50, align: 'center' ,formatter: imgcheckbox },
               { title: '名字', field: 'name', align: 'center', width: 120 },
               { title: 'password', field: 'password', align: 'center', width: 120, hidden: true },
               //{ title: 'orgId', field: 'orgId', align: 'center', width: 120, hidden: true},
               { title: '组织机构', field: 'orgId', align: 'center', width: 120},
               { title: '描述', field: 'description', align: 'center', width: 200}
        ]]
    });
	$("#DelUser").bind("click", UserManage.DelUser);
});
/**
 * 查询
 */
function doSearch(value){
	loadUsers(value);
}

function loadUsers(value) {

    try {
        packQuery(value);
        var json = JSON.stringify(m_user_query);
        $('#UserGrid').datagrid('reload', { 'userQuery': json });

    } catch (ex) {
        alert(ex);
    }
}

function packQuery(value){
	m_user_query.userName = value;
}

function onSelRow(){
	
}
function imgcheckbox(value, rowData, index) {
	//alert(value);
    return value ? "<span><img alt='UnLock' src='resource/icon/menu/unlock.png' /></span>" : "<span><img alt='Lock' src='resource/icon/menu/lock.png'  /></span>";
}
var UserManage = {
//	    AddUser: function () {
//	        ClearForm();
//	        ShowDialog("新增用户", "div_userProfile","","0");
//	        $("#txt_Id").val("0");
//	        document.getElementById("IsUsedCheck").checked = true;
//	    },
//	    EditUser: function () {
//	        var rows = $("#UserGrid").datagrid("getSelected");
//	        if (!rows || rows.length == 0) {
//	            $.messager.alert('操作提示', "请选择要操作的数据!", "warning");
//	            return;
//	        }
//	        if (rows.length > 1) {
//	            $.messager.alert('操作提示', "只能对单挑数据进行操作!", "warning"); 
//	            return;
//	        }
//	        ClearForm();
//	        var Id = rows.Id;
//	        $("#txt_Id").val(Id);
//	        $("#txt_Name").val(rows.Name);
//	        $("#txt_Name").validatebox('validate');
//	        $("#txt_Number").val(rows.Number);
//	        $("#txt_Password").val(rows.Password);
//	        $("#txt_Password").validatebox('validate');
//	        document.getElementById("IsUsedCheck").checked = rows.IsUsed;
//	        document.getElementById("IsAllDataPermissionCheck").checked = rows.IsAllDataPermission;
//	        $("#txt_Description").val(rows.Description);
//
//	        ShowDialog("编辑用户", "div_userProfile", rows.OrganizationId,rows.Id);
//	        $("#txt_OrganizationId").val($('#txt_OrganizationId').combobox('getText'));
//	    },
	    DelUser: function () {
	        var rows = $("#UserGrid").datagrid("getSelected");
	        if (!rows || rows.length == 0) {
	            $.messager.alert('操作提示', "请选择要操作的数据!", "warning");
	            return;
	        }
	        var Id = rows.id;
	        var Name = rows.name;
	        if (Name == "admin") {
	            $.messager.alert('警告提示', '不能删除管理员！', 'warning');
	            return;
	        }
	        deleteRecord(Id);
	    }
//	    //查询
//	    SearchUser: function () {
//	        var userName = $("#UserName").val();
//	        if (userName == "") {
//	            $('#UserGrid').datagrid("reload");
//	        }
//	        $('#UserGrid').datagrid("reload", { 'UserName': userName });
//	    }
	};

function deleteRecord(Id) {
    $.messager.confirm('删除记录', '确认要删除本条记录吗?', function (r) {
        if (r) {
        	$.ajax({
        		url :  "user/deleteUser.do?Id="+Id,
        		type : "POST",
        		dataType : "json",
        		async : false,
        		success : function(req) {
        			if (req) {
        				$('#UserGrid').datagrid("reload");
        			} else {
        				$.messager.alert('删除记录失败ʾ', req.msg, "warning");
        			}
        		}
        	});
        }
    });   
}