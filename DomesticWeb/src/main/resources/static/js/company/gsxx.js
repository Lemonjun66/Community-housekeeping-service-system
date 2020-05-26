//selectMultip.register()方法在js文件夹下selectMultip.js
window.onload = function(){getCompany(); allService();selectMultip.register(); }; 
//获取公司整条字段的方法
function getCompany() {
	$.ajax({
		type : "get",
		//com.xy.ctl CompanyCtl.java getCompany
		url : "/company/getCompany",
		data : {
			
		},
		dataType : "json",
		//返回公司的整条字段
		success : function(data) {
			var name = data.name;
			var type = data.type;
			var content = data.content;
			var remark = data.remark;
			var score = data.score;
			var status = data.status;
			var fileUrl = data.fileUrl;
			document.getElementById('name').value = name;
			document.getElementById('theType').value = type;
			document.getElementById('content').value = content;
			document.getElementById('score').value = score;
			if(status == "0") {
				document.getElementById('status').value = "未提交审核申请";
			}else if(status == "1") {
				document.getElementById('status').value = "信息已上传，管理员审核中";
			}else if(status == "2") {
				document.getElementById('status').value = "正常运营";
			}else if(status == "4") {
				document.getElementById('status').value = "停止服务";
			}
			if(remark == "0") {
				document.getElementById('remark').value = "服务良好";
			}else if(remark == "1") {
				document.getElementById('remark').value = "服务一般";
			}else if(remark == "2") {
				document.getElementById('remark').value = "尚需改进";
			}else if(remark == "3") {
				document.getElementById('remark').value = "停业整改";
			}else {
				document.getElementById('remark').value = "系统会根据贵公司的综合评分给出评价";
			}
		},
		error : function(data){
		},
		async : true,
	});
};

//获取所有服务类型的方法
//这个方法用于更换服务的下拉列表
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
		//返回List<ServiceType>
		success : function(data) {
			$.each(
					data,
					function(i, n) {
						var id = data[i].id;
						var name = data[i].name;

						//拼接div
						//<option>标签指下拉列表的一个选项
						//每遍历1个数据拼接1个字段
						htm += '<option>' + name + '</option>';
					},
			);
			$('#type').html(htm + '<option></option>');
		},
		error : function(data){
		},
		async : true,
	});
};