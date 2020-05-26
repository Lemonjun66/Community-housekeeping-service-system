//manager.html页面加载的方法，获取当前登录的管理员名
//页面加载时执行，获得当前页面登录用户的用户名
window.onload = function(){getName();}; 
function getName() {
	$.ajax({
		type : "get",
		//去ctl包下找ManagerCtl.java中的getManagerUsername
		url : "/manager/getManagerUsername",
		data : {
			
		},
		dataType : "text",
		success : function(data) {
			var username = data;
			$('#userName').html(username);
		},//请求成功，把ManagerCtl.java传来的用户名赋值给username
		//再让manager.html中id="userName"的a标签显示用户名
		error : function(data){
			var username = data;
		},
		//?
		async : true,
	});
};