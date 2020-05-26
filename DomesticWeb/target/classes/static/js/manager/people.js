window.onload = function(){people(1); LoadXinXi(1);};

//括号中的currentPage对应下面data中右边的currentPage
function people(currentPage) {
	var htm = "";
	$.ajax({
		type : "get",
		//去ctl包下PeopleCtl.java中找getAll
		url : "/people/getAll",
		contentType : "application/json",
		data : {
			//左边currentPage是去找people.html中id="currentPage"的span
			//右边currentPage是1
			currentPage : currentPage,
			pageSize : 10
		},
		dataType : "json",
		//请求成功
		success : function(data) {
			$.each(
					data,
					//i第几条数据，n指data
					function(i, n) {
						//取到居民用户的id
						var id = data[i].id;
						//取到居民用户的code，就是用户名
						var code = data[i].code;
						

						//拼接
						htm += '<tr class="text-c">';
						htm += '<td><input type="checkbox" value="1" name=""></td>';
						htm += '<td>' + id + '</td>';
						htm += '<td>' + code + '</td>';
						htm += '<td class="td-manage">';
						//js方法member_del在manager_list.js下，后端方法在ctl包下PeopleCtl.java的delete方法
						//<a title="删除" onclick="member_del('/people/delete',1);" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
						htm += '<a title="删除" onclick="member_del(\'/people/delete\',' + id + ');" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>';
						htm += '</td></tr>';

						$('#people').html(htm);
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
		//去ctl包下PeopleCtl.java中找getAllCount
		url: "/people/getAllCount",
		data: {
		},
		type: "POST",
		dataType: "TEXT",
		//从PeopleCtl.java中返回居民数量,给下面d
		success: function(d) {
			//Math.ceil() 函数返回大于或等于一个给定数字的最小整数
			//maxys是总页数
			maxys = Math.ceil(d/10);
			//给people.html中id="count"的strong显示居民数量，也就是总共几条数据
			$("#count").html(d);
			//给people.html中id="currentPage"的span显示当前第几页，page初始值是1
			$("#currentPage").html('当前第' + page + '页');
		}
	});

	//给上一页添加点击事件
	$("#Previous").click(function() {
		page = page - 1;
		//如果page<1，就已经是第一页了，加载第一页
		if(page < 1) {
			page = 1;
		}
		//给了people方法页数，去加载那一页的数据
		people(page); //加载数据
		//给了LoadXinXi方法页数，去显示总共几条数据，当前第几页
		LoadXinXi(page); //加载分页信息
	})
	//给下一页加点击事件
	$("#Next").click(function() {
		page = page + 1;
		if(page > maxys) {
			page = maxys;
		}
		people(page);; //加载数据
		LoadXinXi(page); //加载分页信息
	})
};