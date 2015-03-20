
var m_customer_id;

var m_year;

var m_customer;

var m_birthdayInfo;

$(function () {
	var args = getUrlArgs();
	
	m_customer_id=parseInt( args["id"]);
	
	m_year =parseInt(args["year"]);
	
	m_customer = parent.m_customer;
	customer2View();
	loadBirthdayInfo();
});


function birthdayInfo2View(){
	$('#txtYear').val(m_birthdayInfo.birthdayYear);
	$('#txtDescription').val(m_birthdayInfo.description);
}

function customer2View(){
	$('#txtName').val(m_customer.name);
	if(m_customer.sex == 0 || m_customer.sex =='0'){
		$('#txtSex').val('女');	
	}else{
		$('#txtSex').val('男');
	}
	$('#txtBirthday').val(m_customer.birthday);
	$('#txtAge').val(m_customer.age);
}

function loadBirthdayInfo(){
	$.ajax({
		url : "customer/getBirthdayInfo.do",
		type : "POST",
		dataType : "json",
		async : false,
		data : {'id': m_customer_id,'year':m_year},
		success : function(req) {
			if (req.isSuccess) {
				m_birthdayInfo = req.data;
				m_year=m_birthdayInfo.birthdayYear;
				birthdayInfo2View();
			} else {
				$.messager.alert("系统提示", req.msg, "error");
			}
		},
		failer : function(a, b) {
			$.messager.alert("消息提示", a, "info");
		},
		error : function(a) {
			$.messager.alert("消息提示", a, "error");
		}
	});
}


function onCBLSave(){
	m_birthdayInfo.birthday=m_customer.birthday;
	m_birthdayInfo.birthdayYear=m_year;
	m_birthdayInfo.custId=m_customer.id;
	m_birthdayInfo.description=$('#txtDescription').val();
	
	$.ajax({
		url : "customer/saveBirthdayInfo.do",
		type : "POST",
		dataType : "json",
		async : false,
		data : {'birthdayInfo': JSON.stringify(m_birthdayInfo)},
		success : function(req) {
			if (req.isSuccess) {
				m_birthdayInfo =req.data;
				m_year =m_birthdayInfo.birthdayYear;
				
				$.messager.alert("提示", "保存成功", "info");
			} else {
				$.messager.alert("系统提示", req.msg, "error");
			}
		},
		failer : function(a, b) {
			$.messager.alert("消息提示", a, "info");
		},
		error : function(a) {
			$.messager.alert("消息提示", a, "error");
		}
	});
}

function loadCustomer(){
	$.ajax({
		url : "custuser/getCustomer.do",
		type : "POST",
		dataType : "json",
		async : false,
		data : {'id':m_customer_id},
		success : function(req) {
			if (req.isSuccess) {
				m_custoemr = req.data;
				customer2View();
			} else {
				$.messager.alert("系统提示", req.msg, "error")
			}
		},
		failer : function(a, b) {
			$.messager.alert("消息提示", a, "info");
		},
		error : function(a) {
			$.messager.alert("消息提示", a, "error");
		}
	});
}