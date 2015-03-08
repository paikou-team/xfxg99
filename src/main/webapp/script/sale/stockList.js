var m_stock_query={};
var m_stock_dlg;
var m_stock_type;

$(function () {
	var args = getUrlArgs();
	
    $("#cmbSendOrg").combobox({
        valueField: 'id',
        textField: 'name',
        editable: false,
        panelHeight: 'auto',
        onSelect: function (record) { m_stock_query.sendOrgId = record.id; }
    });
	
    $("#cmbReceiveOrg").combobox({
        valueField: 'id',
        textField: 'name',
        editable: false,
        panelHeight: 'auto',
        onSelect: function (record) { m_stock_query.receiveOrgId = record.id; }
    });
	
    $("#cmbState").combobox({
        valueField: 'value',
        textField: 'label',
        editable: false,
        panelHeight: 'auto',
        onSelect: function (record) { m_stock_query.state = record.value; },
        data:[{
        	label:'全部',
        	value:0
        },
        {
        	label:'已确认',
        	value:1
        },
        {
        	label:'未确认',
        	value:2
        }]
    });
    
	$('#dgStock').datagrid({
		url:'stock/loadStockList.do',
		queryParams : {
			'stockQuery' : JSON.stringify(m_stock_query)
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
        //toolbar: "#tblStock",
        columns: [[
               { title: 'id', field: 'id', align: 'left', width: 5, hidden: true },
               { title: '入库部门', field: 'stockInOrgName', align: 'left', width: 120 },
               { title: '出库部门', field: 'stockOutOrgName', align: 'left', width: 120 },
               { title: '单据编号', field: 'serialNo', align: 'right', width: 120 },
               { title: '单据日期', field: 'billDate', align: 'right', width: 120 },
        ]]
    });
});

function onSelRow(){
	
}

function loadGoods() {

    try {
        packQuery();
        var json = JSON.stringify(m_goods_query);
        $('#dgGoods').datagrid('reload', { 'goodsQuery': json });

    } catch (ex) {
        alert(ex);
    }
}

function packQuery(){
	m_stock_query.beginTime = $('#dteBeginTime').datebox('getValue');
	m_stock_query.endTime = $('#dteBeginTime').datebox('getValue');
	m_stock_query.serialNo = $('#txtSerialNo').val();
}

function onSelRow(){
	
}


function onStockBillAdd(){
	try {
		m_stock_dlg = art.dialog({
            id: 'dlgStockBillView',
            title: '单据',
            content: "<iframe scrolling='yes' frameborder='0' src='view/sale/stockBill.jsp?billType=" + m_stock_type + "&id=0' style='width:760px;height:460px;'/>",
            //content:"123",
            lock: true,
            initFn: function () {
            }
        });
    } catch (ex) {
        alert(ex);
    }
}