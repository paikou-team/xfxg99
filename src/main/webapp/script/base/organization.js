/**
 * 
 */
$(function () {
    $('#OrganizationTree').treegrid({
    	url: 'organization/getSortList.do',
        fitColumns: true,
        rownumbers: true,
        resizable: true,
        idField: 'path',
        treeField: 'name',
        toolbar:'#organizationtb',
        columns: [[
               { title: '组织机构名称', field: 'name', align: 'left', width: 200 },
               { field: 'id', title: 'Id', width: 130, align: 'center', hidden: true },
               { title: 'level', field: 'level', align: 'center', hidden: true },
               { title: 'parentId', field: 'parentId', align: 'center', hidden: true },
               { title: 'parentName', field: 'parentName', align: 'center', hidden: true },
               { title: 'path', field: 'path', align: 'center', hidden: true },
               { title: 'OrderNo', field: 'OrderNo', align: 'center', hidden: true },
               { title: 'isStock', field: 'isStock', width: 50, align: 'center' ,hidden: true },
               { title: '地址', field: 'address', align: 'center', width: 100 }

        ]]
    });
    
    $("#AddOrganization").bind("click", Organization.AddOrganization);
    $("#EditOrganization").bind("click", Organization.EditOrganization);
    $("#DelOrganization").bind("click", Organization.DelOrganization);
//
    $("#SaveInfo").bind("click", SaveInfo);
    $("#CancelInfo").bind("click", CancelInfo);
});

var Organization = {
	    //新增组织机构
	    AddOrganization: function () {
	        ClearForm();
	        var rows = $('#OrganizationTree').treegrid('getSelected');
	        var root = $('#OrganizationTree').treegrid('getRoot');
	        if (root) {
	            if (!rows || rows.length == 0) {
	                $.messager.alert('操作提示',"请选择需增加的上级部门!", "warning"); 
	                return;
	            }
	            $("#txt_Id").val("0");
	            $("#txt_ParentId").val(rows.id);
	            $("#txt_NodeLevel").val(rows.level);
	            $("#txt_FullPath").val(rows.path + ".");
	            $("#txt_ParentName").val(rows.name);
	        }else
	        {
	            $("#txt_Id").val("0");
	            $("#txt_ParentId").val("0");
	            $("#txt_NodeLevel").val("0");
	            $("#txt_FullPath").val("0");
	            $("#txt_ParentName").val("");
	            $("#txt_Address").val("");
	        }
	        ShowDialog("新增", "div_Organization");
	    },
	    EditOrganization: function () {
	        var rows = $('#OrganizationTree').treegrid('getSelected');
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
	        $("#txt_Address").val(rows.address);
	        $("#txt_ParentId").val(rows.parentId);

	        $("#txt_ParentName").val(rows.parentName);
	        $("#txt_ParentName").validatebox('validate');
	        
	        $("#txt_NodeLevel").val(rows.level);
	        $("#txt_FullPath").val(rows.path);
	        
	        ShowDialog("编辑", "div_Organization");
	        document.getElementById("IsStockCheck").checked = rows.isStock;
	        
	    },
	    DelOrganization: function () {
	        var rows = $('#OrganizationTree').treegrid('getSelected');
	        if (!rows || rows.length == 0) {
	            $.messager.alert('操作提示', "请选择要操作的数据!", "warning");
	            return;
	        }
	        if (rows.length > 1) {
	            $.messager.alert('操作提示', "只能对单挑数据进行操作!", "warning"); 
	            return;
	        }
	        deleteRecord(rows.id);
	    }
	};

var DialogForOrganization;
function ShowDialog(dtitle, contentId, selectId, userId) {
	DialogForOrganization = art.dialog({
        title: dtitle,
        content: document.getElementById(contentId),
        lock: true,
        initFn: function () {
        },
        width: 400,
        height: 200
    });
    //loadComBoxData(selectId);
};
function SaveInfo() {
    var OrganizationObj = {};
    OrganizationObj.id = $("#txt_Id").val();
    OrganizationObj.name = $("#txt_Name").val();
    OrganizationObj.address = $("#txt_Address").val();
    OrganizationObj.level = $("#txt_NodeLevel").val();
    OrganizationObj.path = $("#txt_FullPath").val();
    OrganizationObj.parentId = $("#txt_ParentId").val();
    OrganizationObj.isStock = document.getElementById("IsStockCheck").checked;
    
    $.ajax({
		url :  "organization/saveOrganization.do",
		type : "POST",
		dataType : "json",
		async : false,
		data : OrganizationObj,
		success : function(req) {
			if (req) {
				DialogForOrganization.close();
				$('#OrganizationTree').treegrid("reload");
			} else {
				$.messager.alert('保存记录失败ʾ', req.msg, "warning");
			}
		}
	});
};
function deleteRecord(Id)
{
	$.messager.confirm('删除记录', '确认要删除本借点和该节点所有子节点吗?', function (r) {
        if (r) {
        	$.ajax({
        		url :  "organization/deleteOrg.do?Id="+Id,
        		type : "POST",
        		dataType : "json",
        		async : false,
        		success : function(req) {
        			if (req) {
        				$('#OrganizationTree').treegrid("reload");
        			} else {
        				$.messager.alert('删除记录失败ʾ', req.msg, "warning");
        			}
        		}
        	});
        }
    });
};
function ClearForm() {
    $("#txt_Id").val("");
    $("#txt_Name").val("");
    $("#txt_Address").val("");
    $("#txt_FullPath").val();
    $("#txt_NodeLevel").val();
    document.getElementById("IsStockCheck").checked = false;
};
function CancelInfo() {
    DialogForOrganization.close();
};