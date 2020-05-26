window.onload = function(){getName();}; 
function getName() {
	$.ajax({
		type : "get",
		//com.xy.ctl CompanyCtl.java getCompanyUsername
		url : "/company/getCompanyUsername",
		data : {
			
		},
		dataType : "text",
		//取到公司用户名
		success : function(data) {
			var username = data;
			$('#userName').html(username);
		},
		error : function(data){
			var username = data;
		},
		async : true,
	});
};