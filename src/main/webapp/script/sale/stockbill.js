

$(function () {
	var args = getUrlArgs();
	
	$('#dgStockDetail').datagrid({
        fitColumns: true,
        rownumbers: true,
        resizable: true,
        pagination: false,
        nowrap: false,
        height: 180,
        idField: 'Id',
        singleSelect: true,
        //onDblClickRow: viewStockDetail,
        toolbar: "#stockDetailToolBar",
        columns: [[
               { title: 'Id', field: 'Id', align: 'left', width: 5, hidden: true },
               {
                   title: '类型', field: 'EvidenceType', align: 'left', width: 120, formatter:
                     function (value, rowData, index) {
                         return rowData.Evidence.EvidenceType.Name;
                     }
               },
               {
                   title: '名称', field: 'Name', align: 'left', width: 120, formatter:
                     function (value, rowData, index) {
                         return rowData.Evidence.Name;
                     }
               },
               {
                   title: '型号', field: 'Model', align: 'left', width: 120, formatter:
                     function (value, rowData, index) {
                         return rowData.Evidence.Model;
                     }
               },
               { title: '数量', field: 'Qty', align: 'left', width: 80 },
               { title: '金额', field: 'Amount', align: 'left', width: 100 },
               {
                   title: '仓位', field: 'Storage', align: 'left', width: 220,
                   formatter:
                       function (value, rowData, index) {
                           return rowData.Storage ? rowData.Storage.FullName : null;
                       }
               }
               //{ title: '扣押来源', field: 'Origin', align: 'left', width: 100, formatter: function (value, rowData, index) { return rowData.Evidence.OwnerName; } }
        ]]
    });
	
});