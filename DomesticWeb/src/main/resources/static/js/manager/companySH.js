window.onload = function(){companySH(1); LoadXinXi(1);};

function companySH(currentPage) {
	var htm = "";
	$.ajax({
		type : "get",
		//去ctl包下CompanyCtl.java中找getCompanyByStatus
		url : "/company/getCompanyByStatus",
		contentType : "application/json",
		data : {
			currentPage : currentPage,
			pageSize : 10,
			status : "1",
			serviceType : ""
		},
		dataType : "json",
		//从CompanyCtl.java的getCompanyByStatus回来，成功返回List<company>，里面是全部未审核的公司整条字段
		success : function(data) {
			$.each(
					data,
					function(i, n) {
						var id = data[i].id;
						var name = data[i].name;
						var type = data[i].type == null ? "" : data[i].type;
						var content = data[i].content == null ? "" : data[i].content;
						var fileUrl = data[i].fileUrl == null ? "" : data[i].fileUrl;
						

						//拼接
						htm += '<tr class="text-c">';
						htm += '<td><input type="checkbox" value="1" name=""></td>';
						htm += '<td>' + id + '</td>';
						htm += '<td>' + name + '</td>';
						htm += '<td>' + type + '</td>';
						htm += '<td>' + content + '</td>';
						//这里是下载公司的资质审核文件，去ctl包下CompanyCtl.java中找download
						htm += '<td class="td-status"><span class="label label-success radius"><a href="/company/download?fileUrl=' + fileUrl + '">下载</a></span></td>';
						htm += '<td class="td-manage">';
						//这是点击让公司过审，去ctl包下CompanyCtl.java中找changeStatus，下面有一个pass方法
						htm += '<a title="审核通过" onclick="pass(\'/company/changeStatus\',' + id + ');" class="ml-5" style="text-decoration:none">通过</a>';
						//这是点击让公司不过审，去ctl包下CompanyCtl.java中找changeStatus，下面有一个failed方法
						htm += '&nbsp;&nbsp;<a title="审核不通过" onclick="failed(\'/company/changeStatus\',' + id + ');" class="ml-5" style="text-decoration:none">不通过</a>';
						htm += '</td></tr>';

						$('#companySH').html(htm);
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
			status : "1"
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
		companySH(page); //加载数据
		LoadXinXi(page); //加载分页信息
	})
	//给下一页加点击事件
	$("#Next").click(function() {
		page = page + 1;
		if(page > maxys) {
			page = maxys;
		}
		companySH(page);; //加载数据
		LoadXinXi(page); //加载分页信息
	})
};


//这是上面拼接字段通过审核按钮操作的方法
function pass(url,id){
	layer.confirm('确认通过该审核吗？',function(index){
		$.ajax({
			type: 'POST',
			url: url,
			//把status : "2"审核通过，这个值传过去
			data:{
				id : id,
				status : "2"
			},
			dataType: 'json',
			success: function(data){

				layer.msg('已通过审核!',{icon:1,time:1000});
				location.replace(location.href);
			},
			error:function(data) {
			},
		});		
	});
};

//这是上面拼接字段不通过审核按钮操作的方法
function failed(url,id){
	layer.confirm('确认不通过该审核吗？',function(index){
		$.ajax({
			type: 'POST',
			url: url,
			//把status : "3"审核未通过，这个值传过去
			data:{
				id : id,
				status : "3"
			},
			dataType: 'json',
			success: function(data){

				layer.msg('未通过审核!',{icon:1,time:1000});
				location.replace(location.href);
			},
			error:function(data) {
			},
		});		
	});
};