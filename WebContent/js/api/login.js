function changeToRegister(){
	$("#loginTab").css({"background-color":"#778899","border":"1px solid white"});
	$("#registerTab").css({"background-color":"#3e4d74","border":"1px solid #3e4d74"});
	$("#loginForm").css("display","none");
	$("#registerForm").css("display","inherit")
}
function changeToLogin(){
	$("#registerTab").css({"background-color":"#778899","border":"1px solid white"});
	$("#loginTab").css({"background-color":"#3e4d74","border":"1px solid #3e4d74"});
	$("#registerForm").css("display","none");
	$("#loginForm").css("display","inherit")
}
function postUserInfo(){
	var email_re = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	var phone_re = /^1[3,5,8]\d{9}$/;
	var username = $("#username").val();
	var password = $("#password").val();
	console.log(username);
	if(username==""){
		$("#errmsg h5").text("请填写手机号或登录邮箱");
	}else if(password==""){
		$("#errmsg h5").text("请输入密码");
	}else{
		if(!email_re.test(username)&&!phone_re.test(username)){
			$("#errmsg h5").text("手机号或邮箱格式不正确");
		}else{
			$.ajax({
				type:"post",
				url:"/POSystem/LoginServlet",
				dataType:"text",
				data:{
					"username":username,
					"password":password
				},
				success: function(data){
					//alert(data);
					var response = JSON.parse(data);
					if(response.status==-1){
						$("#errmsg h5").text("该用户名尚未注册");
					}else if(response.status==0){
						$("#errmsg h5").text("用户名或密码错误");
					}else{
						localStorage.setItem("userid",response.data.userid);
						//alert(response.data.userid);
						window.location.href = "./index.html";
					}
				},
				error:function(){
					alert("出现异常");
				}
			});
		}
	}
	
}
	function register(){
		var email_re = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		var phone_re = /^1[3,5,8]\d{9}$/;
		var nickname = $("#nickname").val();
		var newphone = $("#new-phone").val();
		var newemail = $("#new-email").val();
		var newpass = $("#new-password").val();
		var confirmpass = $("#confirm-password").val();
		if(newphone==""||newemail==""||newpass==""||confirmpass==""){
			$("#register-errmsg h5").text("注册信息不能为空");
		}else if(!phone_re.test(newphone)){
			$("#register-errmsg h5").text("错误的手机号格式");
		}else if(!email_re.test(newemail)){
			$("#register-errmsg h5").text("错误的邮箱格式");
		}else if(newpass!=confirmpass){
			$("#register-errmsg h5").text("两次密码输入错误");
		}else{
			$.ajax({
				type:"post",
				url:"/POSystem/RegisterServlet",
				dataType:"text",
				data:{
					"phone":newphone,
					"email":newemail,
					"password":newpass,
					"nickname":nickname
				},
				success:function(data){
					var response = JSON.parse(data);
					if(response.status==0){
						$("#register-errmsg h5").text("该手机号已被注册，请确认");
					}else if(response.status==-1){
						$("#register-errmsg h5").text("该邮箱已被注册，请确认");
					}else if(response.status==-2){
						$("#register-errmsg h5").text("该昵称已被注册，请重新输入");
					}else{
						window.location.href = "./index.html"
					}
				}
			});
		}
		
	};