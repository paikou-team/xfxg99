function loginSubmit(){
	var user = {};
	var username = $.trim($("#txtusername").val());
	if(username == ""){
		$.messager.alert("操作提示","请输入用户名","error");
		return;
	}
	user.name = username;
	var password = $.trim($("#txtpassword").val());
	if(password==""){
		$.messager.alert("操作提示","请输入密码","error");
		return;
	}
	user.password = password;
	$.ajax({
		url : "index/loginAction.do",
		type : "POST",
		dataType : "json",
		async : false,
		data : user,
		success : function(req) { 
			 if(req){
				 window.location.href="index.jsp";
			 }else{
				 $.messager.alert("系统提示","登录错误","info");
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