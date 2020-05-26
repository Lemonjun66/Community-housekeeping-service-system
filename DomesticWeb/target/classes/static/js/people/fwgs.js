window.onload = function(){fwgs(1); LoadXinXi(1);};

function fwgs(currentPage) {
	var htm = "";
	$.ajax({
		type : "get",
		url : "/company/getCompanyByStatus",
		contentType : "application/json",
		data : {
			currentPage : currentPage,
			pageSize : 8,
			status : "2",
			serviceType : ""
		},
		dataType : "json",
		success : function(data) {
			$.each(
					data,
					function(i, n) {
						var id = data[i].id;
						var name = data[i].name;
						var type = data[i].type == null ? "" : data[i].type;
						var content = data[i].content == null ? "" : data[i].content;
						var score = data[i].score;
						
						
						
						//拼接
						htm += '<tr class="text-c">';
						htm += '<td><input type="checkbox" value="1" name=""></td>';
						htm += '<td>' + name + '</td>';
						htm += '<td>' + type + '</td>';
						htm += '<td>' + content + '</td>';
						htm += '<td>' + score + '</td>';

						$('#fwgs').html(htm);
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
		url: "/company/getCompanyCountByStatus",
		data: {
			status : "2"
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
		fwgs(page); //加载数据
		LoadXinXi(page); //加载分页信息
	})
	//给下一页加点击事件
	$("#Next").click(function() {
		page = page + 1;
		if(page > maxys) {
			page = maxys;
		}
		fwgs(page); //加载数据
		LoadXinXi(page); //加载分页信息
	})
};

//这是点击"搜公司"以后执行的代码
$("#searchfwgs").click(function() {
	var htm = '';
	//把搜索的内容赋值给左边serviceType
	var serviceType = $("#serviceType").val();
	$.ajax({
		type : "get",
		//com.xy.ctl CompanyCtl.java getCompanyByStatus
		url : "/company/getCompanyByStatus",
		contentType : "application/json",
		data : {
			currentPage : 1,
			pageSize : 8,
			status : "2",
			serviceType : serviceType,
		},
		dataType : "json",
		success : function(data) {
			$.each(
					data,
					function(i, n) {
						var id = data[i].id;
						var name = data[i].name;
						var type = data[i].type == null ? "" : data[i].type;
						var content = data[i].content == null ? "" : data[i].content;
						var score = data[i].score;
						
						
						
						//拼接
						htm += '<tr class="text-c">';
						htm += '<td><input type="checkbox" value="1" name=""></td>';
						htm += '<td>' + name + '</td>';
						htm += '<td>' + type + '</td>';
						htm += '<td>' + content + '</td>';
						htm += '<td>' + score + '</td></tr>';

						$('#fwgs').html(htm);
		}
		)},
		error : function(data){
			console.log(data);
		},
		async : true,
	})
});