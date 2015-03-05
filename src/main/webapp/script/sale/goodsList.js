
$(function () {
	var args = getUrlArgs();
	
	$('#dgGoods').datagrid({
        fitColumns: true,
        rownumbers: true,
        resizable: true,
        pagination: false,
        nowrap: false,
        height: 180,
        idField: 'Id',
        singleSelect: true,
        onDblClickRow: viewStockDetail,
        toolbar: "#stockDetailToolBar",
        columns: [[
               { title: 'id', field: 'Id', align: 'left', width: 5, hidden: true },
               {
                   title: '����', field: 'EvidenceType', align: 'left', width: 120, formatter:
                     function (value, rowData, index) {
                         return rowData.Evidence.EvidenceType.Name;
                     }
               },
               {
                   title: '����', field: 'Name', align: 'left', width: 120, formatter:
                     function (value, rowData, index) {
                         return rowData.Evidence.Name;
                     }
               },
               {
                   title: '�ͺ�', field: 'Model', align: 'left', width: 120, formatter:
                     function (value, rowData, index) {
                         return rowData.Evidence.Model;
                     }
               },
               { title: '����', field: 'Qty', align: 'left', width: 80 },
               { title: '���', field: 'Amount', align: 'left', width: 100 },
               {
                   title: '��λ', field: 'Storage', align: 'left', width: 220,
                   formatter:
                       function (value, rowData, index) {
                           return rowData.Storage ? rowData.Storage.FullName : null;
                       }
               }
               //{ title: '��Ѻ��Դ', field: 'Origin', align: 'left', width: 100, formatter: function (value, rowData, index) { return rowData.Evidence.OwnerName; } }
        ]]
    });
	
});