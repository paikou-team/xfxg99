
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
               { title: '用户姓名', field: 'name', align: 'center', width: 120 },
               { title: 'password', field: 'password', align: 'center', width: 120, hidden: true },
               //{ title: 'orgId', field: 'orgId', align: 'center', width: 120, hidden: true},
               { title: '组织机构', field: 'orgId', align: 'center', width: 120},
               { title: '用户备注', field: 'description', align: 'center', width: 200}
        ]]
    });
	$("#AddUser").bind("click", UserManage.AddUser);
    $("#EditUser").bind("click", UserManage.EditUser);
	$("#DelUser").bind("click", UserManage.DelUser);
	
	$("#SaveInfo").bind("click", SaveInfo);
    $("#CancelInfo").bind("click", CancelInfo); 
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
	    AddUser: function () {
	        ClearForm();
	        ShowDialog("新增用户", "div_userProfile","","0");
	        $("#txt_Id").val("0");
	        document.getElementById("IsUsedCheck").checked = true;
	    },
	    EditUser: function () {
	        var rows = $("#UserGrid").datagrid("getSelected");
	        if (!rows || rows.length == 0) {
	            $.messager.alert('操作提示', "请选择要操作的数据!", "warning");
	            return;
	        }
	        if (rows.length > 1) {
	            $.messager.alert('操作提示', "只能对单挑数据进行操作!", "warning"); 
	            return;
	        }
	        ClearForm();
	        var Id = rows.id;
	        $("#txt_Id").val(Id);
	        $("#txt_Name").val(rows.name);
	        $("#txt_Name").validatebox('validate');
	        $("#txt_Password").val(rows.password);
	        $("#txt_Password").validatebox('validate');
	        document.getElementById("IsUsedCheck").checked = rows.isUsed;
	        $("#txt_Description").val(rows.description);

	        ShowDialog("编辑用户", "div_userProfile", rows.orgId,rows.id);
	        $("#txt_OrganizationId").val($('#txt_OrganizationId').combobox('getText'));
	    },
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
function SaveInfo() {
    var uName = $("#txt_Name").val();
    if (!uName || uName.length == 0) {
        $.messager.alert('警告提示', '请录入用户名称！', 'warning');
        return;
    }
    if(uName == "admin")
    {
        $.messager.alert('警告提示', '不能修改管理员！', 'warning');
        return;
    }

    var UserObj = {};
    UserObj.Id = $("#txt_Id").val();
    UserObj.Name = $("#txt_Name").val();
    UserObj.Password = $("#txt_Password").val();
    UserObj.Description = $("#txt_Description").val();
    UserObj.IsUsed = document.getElementById("IsUsedCheck").checked;
    
//    var orgid = $('#txt_OrganizationId').combobox('getValue');
//    if ( !orgid ||orgid.length ==  0) {
//        $.messager.alert('警告提示', '请选择部门！', 'warning');
//        return;
//    }
    UserObj.orgId = $('#txt_OrganizationId').combobox('getValue');
    
    $.ajax({
		url :  "user/saveUser.do",
		type : "POST",
		dataType : "json",
		async : false,
		data : UserObj,
		success : function(req) {
			if (req) {
				DialogForUser.close();
				$('#UserGrid').datagrid("reload");
			} else {
				$.messager.alert('保存记录失败ʾ', req.msg, "warning");
			}
		}
	});
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
var DialogForUser;
function ShowDialog(dtitle, contentId, selectId, userId) {
    DialogForUser = art.dialog({
        title: dtitle,
        content: document.getElementById(contentId),
        lock: true,
        initFn: function () {
        },
        width: 500,
        height: 200
    });
    loadComBoxData(selectId);
};
function ClearForm() {
    $("#txt_Id").val("");
    $("#txt_Name").val("");
    $("#txt_Password").val("");
    $("#txt_OrganizationId").val("");
    $("#txt_Description").val("");
};
function CancelInfo() {
    DialogForUser.close();  
}
function loadComBoxData(selectId) {
    $.ajax({
		url : "organization/getList.do",
		type : "POST",
		dataType : "json",
		async : false,
		success : function(req) {
			if (req.isSuccess) {
				var nodes = buildTreeMenu(req.rows);
				var t = $('#txt_OrganizationId').combotree('tree');
				t.tree("loadData", nodes);
				if (!selectId || selectId.length == 0 || selectId == 0) {
	                $('#txt_OrganizationId').combotree('setValue', null);
	            }
	            else {
	                $('#txt_OrganizationId').combotree('setValue', selectId);
	            }
			} 
		}
	});
}
function buildTreeMenu(items){
	var ss=[];
	var cache={};
	
	if(items == null || items.length==0){
		return ss;
	}
	
	var count=items.length;
	
	for (var i = 0; i < count; i++) {
		var node=items[i];
		node.text = node.name;
		cache[node.id]=node;
		if(node.level==1){
			ss.push(node);
		}else{
			var node2=cache[node.parentId];
			if(node2.children==undefined){
				node2.children=[];
			}
			node2.children.push(node);
		}
	}
	return ss;
}