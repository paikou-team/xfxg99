
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
        idField: 'id',
        singleSelect: true,
        onDblClickRow: onSelRow,
        toolbar: "#UserTb",
        columns: [[
               { title: 'Id', field: 'id', align: 'left', width: 5, hidden: true },
               { title: '有效', field: 'isUsed', width: 50, align: 'center' ,formatter: imgcheckbox },
               { title: '用户姓名', field: 'name', align: 'center', width: 120 },
               { title: 'password', field: 'password', align: 'center', width: 120, hidden: true },
               { title: 'isalldatapermission', field: 'isalldatapermission', align: 'center', width: 120, hidden: true},
               { title: '组织机构', field: 'orgId', align: 'center', width: 120, hidden: true},
               { title: '组织机构名称', field: 'orgName', align: 'center', width: 120},
               { title: '用户备注', field: 'description', align: 'center', width: 200}
        ]]
    });
	
	$('#functionTree').tree({
		checkbox : true,
		cascadeCheck : true
	});
	    	
	$("#AddUser").bind("click", UserManage.AddUser);
    $("#EditUser").bind("click", UserManage.EditUser);
	$("#DelUser").bind("click", UserManage.DelUser);
	$("#SearchUser").bind("click", UserManage.SearchUser);
	
	$("#SaveInfo").bind("click", SaveInfo);
    $("#CancelInfo").bind("click", CancelInfo); 
    
    if(args.optType == 1)
    	hideBt();
});


function onSelRow(){
	
}
function imgcheckbox(value, rowData, index) {
	//alert(value);
    return value ? "<span><img alt='UnLock' src='resource/icon/menu/unlock.png' /></span>" : "<span><img alt='Lock' src='resource/icon/menu/lock.png'  /></span>";
}
var UserManage = {
	    AddUser: function () {
	        ClearForm();
	        ShowDialog("新增用户", "div_userProfile",null,"0");
	        $("#txt_Id").val("0");
	        document.getElementById("IsUsedCheck").checked = true;
	        $("#txt_Password").removeAttr("readonly");
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
	        document.getElementById("IsAllDataPermissionCheck").checked = rows.isAllDataPermission;
	        $("#txt_Description").val(rows.description);

	        ShowDialog("编辑用户", "div_userProfile", rows.orgId,rows.id);
	      //初始密码不可编辑状态
//	        $('#txt_Password').validatebox('disabled',false);
	        $("#txt_Password").attr("readonly", true); 
	        $("#txt_OrganizationId").val($('#txt_OrganizationId').combobox('getText'));
	        setRoleTreeChecked(rows.id);
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
//	        $('#UserGrid').datagrid("reload", { 'name': userName });
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
    UserObj.isAllDataPermission = document.getElementById("IsAllDataPermissionCheck").checked;
    
    UserObj.orgId = $('#txt_OrganizationId').combobox('getValue');
    
    var nodes = $('#functionTree').tree('getChecked');
	var ids = '';
	if(nodes.length>0)
	{
		for (var i = 0; i < nodes.length; i++) {
			if (ids != '') 
				ids += ',';
			ids += nodes[i].id;
		}
	}
    
    $.ajax({
		url :  "user/saveUserAndAuthorize.do?ids="+ids,
		type : "POST",
		dataType : "json",
		async : false,
		data : UserObj,
		success : function(seq) {
			if (seq.isSuccess) {
				//saveAutorizeSelected(userId);
				DialogForUser.close();
				$('#UserGrid').datagrid("reload");
			} else {
				$.messager.alert('保存记录失败ʾ', req.msg, "warning");
			}
		}
	});
};
/**
 * 删除用户记录id
 */
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
        				deleteAutorizeRecord(Id);
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
/**
 * 打开弹出窗口
 */
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
    loadRoleTreeData(selectId);
};
/**
 * 清除窗口中填写字段
 */
function ClearForm() {
    $("#txt_Id").val("");
    $("#txt_Name").val("");
    $("#txt_Password").val("");
    //$("#txt_OrganizationId").combotree('setValue', null);
    $("#txt_OrganizationId").combotree('clear');
    $("#txt_Description").val("");
};
/**
 * 关闭弹出窗口
 */
function CancelInfo() {
    DialogForUser.close();  
}
/**
 * 加载组织机构下拉菜单树
 */
function loadComBoxData(selectId) {
    $.ajax({
		url : "organization/getList.do",
		type : "POST",
		dataType : "json",
		async : false,
		success : function(req) {
			if (req.isSuccess) {
				var nodes = buildTreeOrg(req.rows);
				var t = $('#txt_OrganizationId').combotree('tree');
				t.tree("loadData", nodes);
				if (!selectId || selectId.length == 0 || selectId == 0) {
	                //$('#txt_OrganizationId').combotree('setValue', null);
	            }
	            else {
	                $('#txt_OrganizationId').combotree('setValue', selectId);
	            }
			} 
		}
	});
}
/**
 * 构建组织结构树
 */
function buildTreeOrg(items){
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
/**
 * 构建功能权限树
 */
function buildTreeMenu2(items){
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
		if(node.nodeLevel==1){
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
/**
 * 加载功能权限树
 */
function loadRoleTreeData(selectId) {
    $.ajax({
		url : "index/getAllList.do",
		type : "POST",
		dataType : "json",
		async : false,
		success : function(req) {
			if (req.isSuccess) {
				var nodes = buildTreeMenu2(req.rows);
				$('#functionTree').tree("loadData", nodes);
			} 
		}
	});
}
/**
 * 得到index索引号，当前选择用户在authorize中的权限
 */
function setRoleTreeChecked(selectId){
	$.ajax({
		url : "authorize/getListByUserId.do?userId="+selectId,
		type : "POST",
		dataType : "json",
		async : false,
		success : function(req) {
			if (req.isSuccess) {
				setChecked(req.rows,selectId);
			}
		}
	});
}
/**
 * 设置功能权限树的节点为checked状态，当前用户在authorize返回的indexId列表
 */
function setChecked(items,selectId){
	
	if(items == null || items.length==0 || selectId==0 ){
		return ;
	}
	
	var count=items.length;
	
	for (var i = 0; i < count; i++) {
		var data=items[i];
		if(data.userId == selectId ){
			var node = $('#functionTree').tree('find',data.functionId);  
            $('#functionTree').tree('check',node.target);  
		}
	}
}
/**
 * 保存用户的权限到authorize表
 */
function saveAutorizeSelected(userId){
	var nodes = $('#functionTree').tree('getChecked');
	var ids = '';
	if(nodes.length>0)
	{
		for (var i = 0; i < nodes.length; i++) {
			if (ids != '') 
				ids += ',';
			ids += nodes[i].id;
		}

		$.ajax({
			url :  "user/saveAuthorize.do?userId="+userId+"&ids="+ids,
			type : "POST",
			dataType : "json",
			async : false,
			success : function(req) {
				if (req) {

				} else {
					$.messager.alert('保存记录失败ʾ', req.msg, "warning");
				}
			}
		});
	}
}
/**
 * 当用户删除时，删除authorize表中记录的改用户权限记录
 */
function deleteAutorizeRecord(Id){
	$.ajax({
		url : "authorize/deleteListByUserId.do?userId="+Id,
		type : "POST",
		dataType : "json",
		async : false,
		success : function(req) {
			if (req) {
			}
		}
	});
}
/**
 * 查询
 */
function doSearch(value){
	loadUser(value);
}

function loadUser(value) {

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
function hideBt(){
	$("#AddUser").hide();
	$("#DelUser").hide();
	var hideTitle = '基础资料';
	var selected = '权限设置';
    var ctab = $('#tabs').tabs('tabs'), opts;
    for (var i = 0; i < ctab.length; i++) {
        opts = ctab[i].panel('options');
        if (opts.title == hideTitle) {
            ctab[i].hide();
            opts.tab.hide();
        }
    }
    $('#tabs').tabs('select', selected);
}
