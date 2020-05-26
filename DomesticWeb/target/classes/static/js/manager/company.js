window.onload = function(){company(1); LoadXinXi(1);};

function company(currentPage) {
	var htm = "";
	$.ajax({
		type : "get",
		//去ctl包下CompanyCtl.java中找getAll
		url : "/company/getAll",
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
						var name = data[i].name;
						//String status表示公司状态
						var status1 = data[i].status;
						//这里先把公司类型值赋值给左边type，先判断公司类型是否为空，是：就把""赋值给type，不是：就把公司类型赋值给type
						var type = data[i].type == null ? "" : data[i].type;
						var content = data[i].content == null ? "" : data[i].content;
						var score = data[i].score == null ? "" : data[i].score;
						var number = data[i].number == null ? "" : data[i].number;
						var status = ""; 
						//通过status1中的数字来决定显示公司状态的文字
						if(status1 == '0') {
							status = "新创建";
						}else if(status1 == '1') {
							status = "审核中";
						}else if(status1 == '2') {
							status = "审核通过";
						}else if(status1 == '3') {
							status = "审核未通过";
						}else if(status1 == '4') {
							status = "停止服务";
						}
						

						//拼接
						htm += '<tr class="text-c">';
						htm += '<td><input type="checkbox" value="1" name=""></td>';
						htm += '<td>' + id + '</td>';
						htm += '<td>' + name + '</td>';
						htm += '<td>' + type + '</td>';
						htm += '<td>' + content + '</td>';
						htm += '<td>' + score + '</td>';
						htm += '<td>' + number + '</td>';
						htm += '<td class="td-status"><span class="label label-success radius">' + status + '</span></td>';
						htm += '<td class="td-manage">';
						//1个删除按键，
						htm += '<a title="删除" onclick="member_del(\'/company/delete\',' + id + ');" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>';
						htm += '</td></tr>';

						$('#company').html(htm);
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
		//去ctl包下CompanyCtl.java中找getAllCount
		url: "/company/getAllCount",
		data: {
		},
		type: "POST",
		dataType: "TEXT",
		//从CompanyCtl.java中返回公司数量,给下面d
		success: function(d) {
			//Math.ceil() 函数返回大于或等于一个给定数字的最小整数
			//maxys是总页数
			maxys = Math.ceil(d/10);
			//给company.html中id="count"的strong显示公司数量，也就是总共几条数据
			$("#count").html(d);
			//给company.html中id="currentPage"的span显示当前第几页，page初始值是1
			$("#currentPage").html('当前第' + page + '页');
		}
	});

	//给上一页添加点击事件
	$("#Previous").click(function() {
		//如果page<1，就已经是第一页了，加载第一页
		page = page - 1;
		if(page < 1) {
			page = 1;
		}
		//给了company方法页数，去加载那一页的数据
		company(page); //加载数据
		//给了LoadXinXi方法页数，去显示总共几条数据，当前第几页
		LoadXinXi(page); //加载分页信息
	})
	//给下一页加点击事件
	$("#Next").click(function() {
		page = page + 1;
		if(page > maxys) {
			page = maxys;
		}
		company(page);; //加载数据
		LoadXinXi(page); //加载分页信息
	})
};