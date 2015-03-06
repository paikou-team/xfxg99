
var m_user_query={userName:null};
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
               { title: 'id', field: 'id', align: 'left', width: 5, hidden: true },
               { title: '有效', field: 'isUsed', width: 50, align: 'center' ,formatter: imgcheckbox },
               { title: '名字', field: 'name', align: 'center', width: 120 },
               { title: 'password', field: 'password', align: 'center', width: 120, hidden: true },
               //{ title: 'orgId', field: 'orgId', align: 'center', width: 120, hidden: true},
               { title: '组织机构', field: 'orgId', align: 'center', width: 120},
               { title: '描述', field: 'description', align: 'center', width: 200}
        ]]
    });
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