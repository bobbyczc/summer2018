<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery-3.3.1.js"></script>
<script src="js/bootstrap.js"></script>
<title>Insert title here</title>
</head>
<body>
	<input id= "key" type="text"></input>
	<button id="sub"type="button">提交</button>
	<p></p>
</body>
</html>

<script>
 $("#sub").click(function(){
	 var keyword = $("#key").val();
	 $.ajax({
		 type:"post",
		 url:"/POSystem/CalHotValueServlet",
	 	 contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	 	 data:{
	 		 "keyword":keyword,
	 		 "startDate":"2018-07-22",
	 		 "endDate":"2018-07-26"
	 	 },
	 	 
	 	 success: function(data){
	 		alert(data)
	 	 }
	 })
 })

</script>