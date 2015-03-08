
var m_goods_query={goodsName:null};

var m_callType;

$(function () {
	var args = getUrlArgs();
	
	m_callType=args.callType;
	var dgPageSize=args.pageSize;
	
	if(args['callType']==2){
		
	}
	
	$('#dgGoods').datagrid({
		url:'goods/loadGoodsList.do',
		queryParams : {
			'goodsQuery' : JSON.stringify(m_goods_query)
		},
        fitColumns: true,
        rownumbers: true,
        resizable: true,
        pagination: true,
        pageNumber: 1,
        pageSize: dgPageSize,
        nowrap: false,
        idField: 'id',
        singleSelect: true,
        onDblClickRow: onSelRow,
        toolbar: "#stockDetailToolBar",
        columns: [[
               { title: 'id', field: 'id', align: 'left', width: 5, hidden: true },
               {
                   title: '类型', field: 'catName', align: 'left', width: 120 },
               {
                   title: '名称', field: 'name', align: 'left', width: 180 },
               {
                   title: '价格', field: 'shopPrice', align: 'right', width: 60, formatter:
                     function (value, rowData, index) {
                         return value;
                     }
               }
        ]]
    });
});
/**
 * 查询
 */
function doSearch(value){
	loadGoods(value);
}

function loadGoods(value) {

    try {
        packQuery(value);
        var json = JSON.stringify(m_goods_query);
        $('#dgGoods').datagrid('reload', { 'goodsQuery': json });

    } catch (ex) {
        alert(ex);
    }
}

function packQuery(value){
	m_goods_query.goodsName = value;
}

function onSelRow(index,row){
	if(m_callType==2){
		if(window.parent.onSelGoods){
			window.parent.onSelGoods(row);
			window.parent.m_goodsListDlg.close();
		}
	}
}
