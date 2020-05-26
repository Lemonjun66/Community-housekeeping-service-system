window.onload = function(){wwcdd(1); LoadXinXi(1);};

function wwcdd(currentPage) {
	var htm = "";
	$.ajax({
		type : "get",
		//com.xy.ctl OrderCtl.java getOrderByPeopleAndStatus
		url : "/order/getOrderByPeopleAndStatus",
		contentType : "application/json",
		data : {
			currentPage : currentPage,
			pageSize : 8,
			//订单状态  0-未完成  1-未评分  2-已完成，这里取未完成
			status : "0",
		},
		dataType : "json",
		success : function(data) {
			$.each(
					data,
					function(i, n) {
						var id = data[i].id;
						var company = data[i].company;
						var time = data[i].time == null ? "" : data[i].time;
						var type = data[i].type == null ? "" : data[i].type;
						
						
						
						//拼接
						htm += '<tr class="text-c">';
						htm += '<td><input type="checkbox" value="1" name=""></td>';
						htm += '<td>' + id + '</td>';
						htm += '<td>' + company + '</td>';
						htm += '<td>' + time + '</td>';
						htm += '<td>' + type + '</td>';
						//com.xy.ctl OrderCtl.java changeStatus
						htm += '<td><a title="已完成" onclick="finish(\'/order/changeStatus\',' + id + ');" class="ml-5" style="text-decoration:none">已完成</a>';
						htm += '&nbsp;&nbsp;<a title="取消" onclick="cancel(\'/order/delete\',' + id + ');" class="ml-5" style="text-decoration:none">取消</a></td>';
						htm += '<td><a href="/people/chat">聊天</a></td>';
						htm += '</tr>';
						$('#wwcdd').html(htm);
					},
			);
		},
		error : function(data){
		},
		async : true,
	});
};


function LoadXinXi(currentPage) {       //加载分页信息
	var minys = 1;
	var maxys = 1;
	var page = currentPage;

	//查总页数
	$.ajax({
		async: false,
		url: "/order/getOrderCountByPeopleAndStatus",
		data: {
			status : "0"
		},
		type: "POST",
		dataType: "TEXT",
		success: function(d) {
			maxys = Math.ceil(d/8);
			$("#count").html(d);
			$("#currentPage").html('当前第' + page + '页');
		}
	});

	//给上一页添加点击事件
	$("#Previous").click(function() {
		page = page - 1;
		if(page < 1) {
			page = 1;
		}
		wwcdd(page); //加载数据
		LoadXinXi(page); //加载分页信息
	})
	//给下一页加点击事件
	$("#Next").click(function() {
		page = page + 1;
		if(page > maxys) {
			page = maxys;
		}
		wwcdd(page); //加载数据
		LoadXinXi(page); //加载分页信息
	})
};


function cancel(url,id){
	layer.confirm('确认要取消订单吗？',function(index){
		$.ajax({
			type: 'POST',
			url: url,
			data:{
				id : id,
			},
			dataType: 'json',
			success: function(data){

				layer.msg('已取消!',{icon:1,time:1000});
				location.replace(location.href);
			},
			error:function(data) {
			},
		});		
	});
};

function finish(url,id){
	layer.confirm('确认将该订单标记为已完成吗？',function(index){
		$.ajax({
			type: 'POST',
			url: url,
			data:{
				id : id,
				status : "1"
			},
			dataType: 'json',
			success: function(data){

				layer.msg('已修改!',{icon:1,time:1000});
				location.replace(location.href);
			},
			error:function(data) {
			},
		});		
	});
};