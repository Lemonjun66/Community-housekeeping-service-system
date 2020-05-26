window.onload = function(){order(1); LoadXinXi(1);};

function order(currentPage) {
	var htm = "";
	$.ajax({
		type : "get",
		//去com.xy.ctl OrderCtl.java getAll，需返回PageBean<order>
		url : "/order/getAll",
		contentType : "application/json",
		data : {
			currentPage : currentPage,
			pageSize : 10
		},
		dataType : "json",
		success : function(data) {
			$.each(
					data,
					function(i, n) {
						var id = data[i].id;
						var people = data[i].people;
						var company = data[i].company;
						var time = data[i].time == null ? "" : data[i].time;
						var type = data[i].type == null ? "" : data[i].type;
						var score1 = data[i].score == null ? "" : data[i].score;
						var phone = data[i].phone == null ? "" : data[i].phone;
						var name = data[i].name == null ? "" : data[i].name;
						var wechat = data[i].wechat == null ? "" : data[i].wechat;
						var content = data[i].content == null ? "" : data[i].content;
						var status1 = data[i].status == null ? "" : data[i].status;
						var status = ""; 
						var score = "";
						//status:订单状态  0-未完成  1-未评分  2-已完成
						if(status1 == '0') {
							status = "未完成";
							score = "未评分";
						}else if(status1 == '1') {
							status = "未评分";
							score = "未评分";
						}else if(status1 == '2') {
							status = "已完成";
							score = score1;
						}
						

						//拼接
						htm += '<tr class="text-c">';
						htm += '<td><input type="checkbox" value="1" name=""></td>';
						htm += '<td>' + id + '</td>';
						htm += '<td>' + people + '</td>';
						htm += '<td>' + company + '</td>';
						htm += '<td>' + time + '</td>';
						htm += '<td>' + type + '</td>';
						htm += '<td>' + name + '</td>';
						htm += '<td>' + phone + '</td>';
						htm += '<td>' + wechat + '</td>';
						htm += '<td>' + content + '</td>';
						htm += '<td>' + score + '</td>';
						htm += '<td class="td-status"><span class="label label-success radius">' + status + '</span></td>';
						htm += '<td class="td-manage">';
						htm += '<a title="删除" onclick="member_del(\'/order/delete\',' + id + ');" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>';
						htm += '</td></tr>';

						$('#order').html(htm);
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
		url: "/order/getAllCount",
		data: {
		},
		type: "POST",
		dataType: "TEXT",
		success: function(d) {
			maxys = Math.ceil(d/10);
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
		order(page); //加载数据
		LoadXinXi(page); //加载分页信息
	})
	//给下一页加点击事件
	$("#Next").click(function() {
		page = page + 1;
		if(page > maxys) {
			page = maxys;
		}
		order(page);; //加载数据
		LoadXinXi(page); //加载分页信息
	})
};