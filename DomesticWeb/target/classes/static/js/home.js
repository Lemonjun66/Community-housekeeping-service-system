/*$(document).ready(function(){
    checkUserName();
});
//验证用户名是否存在
function checkUserName(){
	//当焦点移出用户名输入框时
	$("#username").blur(function(){
	    var userName = $.trim($("#username").val());
	    // 判断是否为空及空格
	    if(userName == ""){
	    	alert("11111");
	      return;
	    }
	     
	    // 校验用户名是否存在,该URL可以返回该用户名存在的数量
	    var url = 'checkUserName?username='+ userName;
	    // 清空用户名表单提示信息内容
	    var msgObj = $(this).next(".msg");
	    msgObj.html("");
	    // 使用ajax去后台判断该用户名在数据库中是否存在
	    $.ajax({
	        url: url,
	        data:{
	        	
	        },
	        type : 'GET',
	        dataType:'json',
	        contentType:'application/json',
	        succsess:function(data){
	            // 数据库中存在该用户名
	            if(data*1 > 0){
	                // 将错误信息添加至相应位置
	                msgObj.html("该用户名已经存在");
	            } else {
	                msgObj.html("该用户名可以使用");
	            }
	        },
	        error:function(){
	                msgObj.html("校验用户名出现错误");
	        }
	    });
	});
}

*/


    $(function(){
        //注册时检测用户名是否有问题
    	$('#username').focusout(function(){
        	var flag = false;
        	//把前端id="username"的input的value值赋给username
        	var username = $('#username').val();
        	if(username.length<2||username.length>20){
                $(".msg").html('<font color="red">注册名长度2~20</font>');
        	}else{
                $.ajax({
                    //这里验证是否重名，去ctl包下LoginCtl.java找checkUserName
                	//再把username赋值给data中的username
                	url:'/login/checkUserName?username='+ username,
                    type:'post',
                    data:{
                    	username:$('#username').val()
                    	},
                    	//执行下面之前，先去checkUserName方法
                    	//从checkUserName那里回来，取到了重复名data=1(用户名已存在)，没取到重复名data=-1(用户名可用)
                    success:function(data){
                    	if(data != -1) {
                            $('.msg').html('<font color="red">该用户名已存在</font>'); 
                        } else{
                            $('.msg').html('<font color="green">该用户名可用</font>');
                            flag = true;
                            }
                    },
                    error:function(data) {
                    	
                    }
                });
        	}
            //如果用户名可用，就能够注册
        	//前端id="send-btn1"的input是注册按钮，disabled指不能点，不能点=false就是能点
        	if (flag) {
            	document.getElementById("send-btn1").disabled = false;
            } else {
            	document.getElementById("send-btn1").disabled = true;
            }
        });

        //从register.html过来，注册时检测密码是否符合要求
    	$('#password').focusout(function(){
        	var flag = false;
        	//把register.html中id=password的input的value赋给左边的password
        	var password = $('#password').val();	
        	if(password.length<8||password.length>20){
        		//这个id=tishi1的span在register.html的密码边上
        		$(".tishi1").html('<font color="red">密码长度应在8~20之间</font>');
        	}else{
        		$(".tishi1").html('<font color="green">密码格式正确</font>');        		
        	}
        	
            if (flag) {
            	document.getElementById("send-btn1").disabled = false;
            } else {
            	document.getElementById("send-btn1").disabled = true;
            }
        });
    });
    
    
    //登录前校验用户名和密码是否正确
    //从index.html过来
    function loginCheck(){
    	var name = $("#username6").attr("value");    //用户名 attr返回被选元素的属性值
    	var pwd = $("#password6").attr("value");      //密码
    	var verifyCode =  $("#yzm").attr("value");     //验证码
    	var datas = "";                  //返回来的结果
	    $.ajax({  
            type: "post",  
            contentType:"application/string",
            async:false,
            url : "/login/beforeLogin?username="+name+"&pwd="+pwd+"&verifyCode="+verifyCode, 
            //这里去找后端LoginCtl.java下beforeLogin
            success: function (data) {   
//            	datas = eval("("+data+")");
            	datas = data;//data就是username这些数据，把数据给datas
          	},//请求成功，基本上都是成功
          	error: function (data) {
          	}//请求失败
       	});
	    
	    //LoginCtl.java中beforeLogin执行完来这里
	    if(datas == "nameFalse"){                 //用户名不正确
	    	$('.msg1').html('<font color="red">&nbsp;&nbsp;&nbsp;用户名不存在</font>');
	    	$('.msg3').html('<font color="green">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;验证码正确</font>');
    		//返回给index.html的form中的onsubmit
	    	return false;
        }else if(datas == "pwdFalse"){            //密码不正确
        	$('.msg2').html('<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;密码错误</font>');
	    	$('.msg1').html('<font color="green">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户名存在</font>');
	    	$('.msg3').html('<font color="green">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;验证码正确</font>');
    		return false;
        }else if(datas == "yzmFalse"){            //验证码不正确
        	$('.msg3').html('<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;验证码错误</font>');
    		return false;
        }else{
        	return true;
        }
        
    }
