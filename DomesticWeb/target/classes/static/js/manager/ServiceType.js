window.onload = function(){serviceType(1); LoadXinXi(1);};

//括号中的currentPage对应下面data中右边的currentPage
function serviceType(currentPage) {
	var htm = "";
	$.ajax({
		type : "get",
		//去ctl包下ServiceTypeCtl.java中找getAllFY
		url : "/serviceType/getAllFY",
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
						

						//拼接
						htm += '<tr class="text-c">';
						htm += '<td><input type="checkbox" value="1" name=""></td>';
						htm += '<td>' + id + '</td>';
						htm += '<td>' + name + '</td>';
						htm += '<td class="td-manage">';
						htm += '<a title="删除" onclick="member_del(\'/serviceType/delete\',' + id + ');" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>';
						htm += '</td></tr>';

						$('#ServiceType').html(htm);
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
		//ctl包下
		url: "/serviceType/getAllCount",
		data: {
		},
		type: "POST",
		dataType: "TEXT",
		//获得服务类型数
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
		serviceType(page); //加载数据
		LoadXinXi(page); //加载分页信息
	})
	//给下一页加点击事件
	$("#Next").click(function() {
		page = page + 1;
		if(page > maxys) {
			page = maxys;
		}
		serviceType(page);; //加载数据
		LoadXinXi(page); //加载分页信息
	})
};

/*点击弹出按钮*/
//从ServiceType.html过来，
function popBox1() {
    var popBox = document.getElementById("popBox");
    var popLayer = document.getElementById("popLayer");
    //块状
    popBox.style.display = "block";
    popLayer.style.display = "block";
};

/*点击关闭按钮*/
function closeBox() {
    var popBox = document.getElementById("popBox");
    var popLayer = document.getElementById("popLayer");
    popBox.style.display = "none";
    popLayer.style.display = "none";
}