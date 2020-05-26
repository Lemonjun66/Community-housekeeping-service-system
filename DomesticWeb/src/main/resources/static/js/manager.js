window.onload = function(){a(); b(); c();}; 

function a() {
		var htm = '<tr><td align="center">编号</td><td align="center">姓名</td><td align="center">用户名</td><td align="center">所在区域</td><td align="center">年龄</td"><td align="center">是否展示</td><td align="center">操作</td"></tr>';
			$.ajax({
					type : "get",
					url : "/teacher/getTeacherAll",
					contentType : "application/json",
					data : {
						
					},
					dataType : "json",
 					success : function(data) {
						$.each(
								data,
								function(i, n) {
									var id = data[i].id;
									var name = data[i].name;
									var username = data[i].userName;
									var sex = data[i].sex;
									var area = data[i].area;
									var age = data[i].age;
									var state = data[i].state;
									
									//拼接div
									htm += '<tr>';
									htm += '<td align="center">' + id + '</td>';
									htm += '<td align="center">' + name + '</td>';
									htm += '<td align="center">' + username + '</td>';
									htm += '<td align="center">' + area + '</td>';
									htm += '<td align="center">' + age + '</td>';
									htm += '<td align="center">' + state + '</td>';
									htm += '<td align="center"><a href="" onclick="javascript:return del(' + id + ');">删除</a>&nbsp;&nbsp;<a href="">修改</a>&nbsp;&nbsp;<a href="">查看详情</a></td>';
									htm += '</tr>';
									$('#table').html(htm);
								},
						);
					},
					error : function(data){
					},
					async : true,
				});
	};
	

	
function b() {
		htm = '<a class="selected" href="/teacher/getAll" ka="sel-business-0">不限</a>';
		$.ajax({
			type : "get",
			url : "/other/address",
			contentType : "application/json",
			data : {

			},
			dataType : "json",
				success : function(data) {
				$.each(
						data,
						function(i, n) {
							var id = data[i].id;
							var place = data[i].place;
							
							//拼接div
							htm += '<a href="/teacher/getByArea?area=' + place + '" ka="sel-business-0">' + place + '</a>';
							$('#address3').html(htm);
						},
				);
			},
			error : function(data){
			},
			async : true,
		});
	};
	
	
		
function c() {
		$.ajax({
			type : "get",
			url : "/manager/getUsername",
//			contentType : "application/json",
			data : {
				
			},
			dataType : "text",
			success : function(data) {
				var username = data;
				$('#userName3').html(username);
			},
			error : function(data){
				var username = data;
				alert(username);
			},
			async : true,
		});
	};
	
	function del(id) {
		var msg = "您确定要删除吗？\n\n请确认！";
		if (confirm(msg)==true){
		    $.ajax({
		        type:"post",
		        url:"/teacher/delete?id=" + id,
		        dataType:'text',
				data : {
					
				},
		        success:function(data){
		        	window.open("/teacher/refresh");
		            return true;
		        },
		        error:function(data){
		        	return false;
		        },
				async : false
		      })
		}else{
		return false;
		}
	};
	
//	function refresh() {
//		$.ajax({
//			type:"post",
//			url:"/teacher/refresh",
//			data : {
//
//			},
//			success:function(data){
//				document.write(data);
//				return true;
//			},
//			error:function(data){
//				return false;
//			},
//			async : false
//		})
//	}
