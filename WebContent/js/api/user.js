

function userbase(method,url,callback){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = callback;
	xhr.open(method,url,true);
	
}


function login(){
	var xhr = new XMLHttpRequest();
	xhr.open("get","/LoginServlet",true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState==4&xhr.status==200){
			console.log(xhr.responseText);
		}
	};
}