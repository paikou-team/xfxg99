function loginSubmit(){
	 
	var username = $.trim($("#txtusername").val());
	if(username == ""){
		$.messager.alert("操作提示","请输入用户名","error");
		return;
	} 
	var password = $.trim($("#txtpassword").val());
	if(password==""){
		$.messager.alert("操作提示","请输入密码","error");
		return;
	}
	var hash = hex_md5(password);    
	$.ajax({
		url : "index/loginAction.do",
		type : "POST",
		dataType : "json",
		async : false,
		data : {"username":username,"password":hash},
		success : function(req) { 
			 if(req.isSuccess){
				 if(req.data){
					 
				 }
				 window.location.href="index.jsp";
			 }else{
				 $.messager.alert("系统提示",req.msg,"info");
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