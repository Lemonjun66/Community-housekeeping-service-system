window.onload = function(){allService();};

function allService() {
	var htm = "";
	$.ajax({
		type : "get",
		//com.xy.ctl ServiceTypeCtl.java getAll
		url : "/serviceType/getAll",
		contentType : "application/json",
		data : {
			
		},
		dataType : "json",
		//返回所有服务类型List集合List<ServiceType>
		//完成拼接给"服务"的下拉列表
		success : function(data) {
			$.each(
					data,
					function(i, n) {
						var id = data[i].id;
						var name = data[i].name;

						//拼接div
						htm += '<option>' + name + '</option>';
						$('#serviceType').html(htm);
					},
			);
		},
		error : function(data){
		},
		async : true,
	});
};

//根据服务类型勾选公司
$("#company").focus(function(){
	var name = $("#serviceType").val();
	var htm = "";
	$.ajax({
		type : "get",
		//com.xy.ctl CompanyCtl.java getCompanyByStatus
		url : "/company/getCompanyByStatus",
		contentType : "application/json",
		data : {
			currentPage : 1,
			pageSize : 100,
			status : "2",
			serviceType : name
		},
		dataType : "json",
		success : function(data) {
			
			$.each(
					data,
					function(i, n) {
						var id = data[i].id;
						var name = data[i].name;

						//拼接div
						htm += '<option>' + name + '</option>';
						$('#company').html(htm);
					},
			);
		},
		error : function(data){
		},
		async : true,
	});	
});

