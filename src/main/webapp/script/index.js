var m_index_user;

var m_index_iconStyles = {};

$(function() {
	
	getCurrentUser();
	
	var args = getUrlArgs();

	//isSignIn(gSetCurrentUserCallback);

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
				var nodes = buildTreeMenu(req.rows);
				$('#treeMenu').tree("loadData", nodes);
			} else {
				$.messager.alert('提示ʾ', req.msg, "warning");
			}
		}
	});
}
/**
 * 
 */
function onTreeMenuDblClick(row) {
	var src = null;

	getCurrentUser();
	
	switch (row.funcKey) {
	case "shop_stockin":
		src = "view/sale/stockList.jsp?billType=10";
		break;
	case "shop_stockout":
		src = "view/sale/stockList.jsp?billType=11";
		break;
	case "shop_inventory":
		src="view/sale/inventory.jsp";
		break;
	case "shop_transfer":
		src = "view/sale/stockList.jsp?billType=12";
		break;
	case "sys_user":
		src = "view/base/user.jsp?optType=0";
		break;
	case "sys_shop":
		src = "view/base/organization.jsp";
		break;
	case "sys_setting":
		src = "view/base/organization.jsp";
		break;
	case "cust_recharge":
		src = "view/sale/chargeList.jsp?optType=0";
		break;
	case "biz_recharge_confirm":
		src = "view/sale/chargeList.jsp?optType=1";
		break;
	case "biz_recharge_noconfirm":
		src = "view/sale/chargeList.jsp?optType=2";
		break;
	case "sys_authorize":
		src = "view/base/user.jsp?optType=1";
		break;
	case "shop_sale":
		src = "view/sale/saleList.jsp";
		break;

	}

	$("#ifrContent").attr("src", src);
}
/**
 * 建立主菜单
 * 
 * @param items
 * @returns {Array}
 */
function buildTreeMenu(items) {
	var ss = [];
	var cache = {};

	if (items == null || items.length == 0) {
		return ss;
	}

	var count = items.length;

	for ( var i = 0; i < count; i++) {
		var node = items[i];
		node.text = node.name;
		cache[node.id] = node;
		createIconStyle(node);
		if (node.nodeLevel == 1) {
			ss.push(node);
		} else {
			var node2 = cache[node.parentId];
			if (node2.children == undefined) {
				node2.children = [];
			}
			node2.children.push(node);
		}
	}
	return ss;
}

function createIconStyle(node) {
	if (node.iconCls == undefined || node.iconCls == null) {
		if (node.icon != null && node.icon.length > 0) {
			var classId = "icon_main_menu_" + node.id;
			var tmpId = m_index_iconStyles[classId];
			if (tmpId == undefined || tmpId == null) {
				var style = "."
						+ classId
						+ "{	background:url('"
						+ location.href
						+ node.icon
						+ "');background-size:contain; width:16px; height:16px}";
				createStyle(style);
				m_index_iconStyles[classId] = classId;
			}
			node.iconCls = classId;
		}
	}
}

function createStyle(css) {
	try { // IE兼容
		var style = document.createStyleSheet();
		style.cssText = css;
	} catch (e) { // Firefox,Opera,Safari,Chrome兼容
		var style = document.createElement("style");
		style.type = "text/css";
		style.textContent = css;
		document.getElementsByTagName("HEAD").item(0).appendChild(style);
	}
}

function iframeSize() {
	var ifm = document.getElementById("ifrContent");
	var subWeb = document.frames ? document.frames["ifrContent"].document
			: ifm.contentDocument;
	if (ifm != null && subWeb != null) {
		ifm.height = subWeb.body.scrollHeight;
		ifm.width = subWeb.body.scrollWidth;
	}
}
