window.onload = function(){sddd(1); LoadXinXi(1);};

function sddd(currentPage) {
	var htm = "";
	$.ajax({
		type : "get",
		//com.xy.ctl OrderCtl.java getOrderByCompanyAndStatus
		url : "/order/getOrderByCompanyAndStatus",
		contentType : "application/json",
		data : {
			currentPage : currentPage,
			pageSize : 8,
			status : "0",
		},
		dataType : "json",
		success : function(data) {
			$.each(
					data,
					function(i, n) {
						var id = data[i].id;
						var people = data[i].people;
						var time = data[i].time == null ? "" : data[i].time;
						var type = data[i].type == null ? "" : data[i].type;
						var name = data[i].name == null ? "" : data[i].name;
						var phone = data[i].phone == null ? "" : data[i].phone;
						var wechat = data[i].wechat == null ? "" : data[i].wechat;
						var content = data[i].content == null ? "" : data[i].content;
						
						
						
						//拼接
						htm += '<tr class="text-c">';
						htm += '<td><input type="checkbox" value="1" name=""></td>';
						htm += '<td>' + id + '</td>';
						htm += '<td>' + people + '</td>';
						htm += '<td>' + time + '</td>';
						htm += '<td>' + type + '</td>';
						htm += '<td>' + name + '</td>';
						htm += '<td>' + phone + '</td>';
						htm += '<td>' + wechat + '</td>';
						htm += '<td>' + content + '</td>';
						htm += '<td><a href="/company/chat">聊天</a></td>';
						htm += '</tr>';
						$('#sddd').html(htm);
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
		url: "/order/getOrderCountByCompanyAndStatus",
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
		sddd(page); //加载数据
		LoadXinXi(page); //加载分页信息
	})
	//给下一页加点击事件
	$("#Next").click(function() {
		page = page + 1;
		if(page > maxys) {
			page = maxys;
		}
		sddd(page); //加载数据
		LoadXinXi(page); //加载分页信息
	})
};