window.onload = function(){getName();}; 
function getName() {
	$.ajax({
		type : "get",
		url : "/people/getPeopleUsername",
		data : {
			
		},
		dataType : "text",
		success : function(data) {
			var username = data;
			$('#userName').html(username);
		},
		error : function(data){
			var username = data;
			alert(username);
		},
		async : true,
	});
};