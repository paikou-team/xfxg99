
var m_index_user;


$(function() {
	var args = getUrlArgs();
	
	$('#treeMenu').tree({
		checkbox : false,
		cascadeCheck : true,
		onClick : onTreeMenuDblClick
	});
	
	loadMenu();
});


function loadMenu() {
	$.ajax({
		url : "index/getList.do",
		type : "POST",
		dataType : "json",
		async : false,
		success : function(req) {
			if (req.isSuccess) {
				var nodes = buildTree(req.rows);
				$('#treeMenu').tree("loadData", nodes);
			} else {
				$.messager.alert('ÌáÊ¾', req.msg, "warning");
			}
		}
	});
}
/**
 * Ë«»÷²Ëµ¥
 */
function onTreeMenuDblClick(){
	
}