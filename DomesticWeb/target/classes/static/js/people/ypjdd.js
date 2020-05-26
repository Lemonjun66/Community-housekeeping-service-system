window.onload = function(){ypjdd(1); LoadXinXi(1);};

function ypjdd(currentPage) {
	var htm = "";
	$.ajax({
		type : "get",
		url : "/order/getOrderByPeopleAndStatus",
		contentType : "application/json",
		data : {
			currentPage : currentPage,
			pageSize : 8,
			status : "2",
		},
		dataType : "json",
		success : function(data) {
			$.each(
					data,
					function(i, n) {
						var id = data[i].id;
						var company = data[i].company;
						var score = data[i].score;
						var time = data[i].time == null ? "" : data[i].time;
						var type = data[i].type == null ? "" : data[i].type;
						document.getElementById("demo").value=id;
						
						
						//拼接
						htm += '<tr class="text-c">';
						htm += '<td><input type="checkbox" value="1" name=""></td>';
						htm += '<td>' + id + '</td>';
						htm += '<td>' + company + '</td>';
						htm += '<td>' + time + '</td>';
						htm += '<td>' + type + '</td>';
						htm += '<td>' + score + '</td>';
						htm += '</tr>';
						$('#ypjdd').html(htm);
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
		ywcdd(page); //加载数据
		LoadXinXi(page); //加载分页信息
	})
	//给下一页加点击事件
	$("#Next").click(function() {
		page = page + 1;
		if(page > maxys) {
			page = maxys;
		}
		ywcdd(page); //加载数据
		LoadXinXi(page); //加载分页信息
	})
};

/*点击弹出按钮*/
function popBox1() {
    var popBox = document.getElementById("popBox");
    var popLayer = document.getElementById("popLayer");
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