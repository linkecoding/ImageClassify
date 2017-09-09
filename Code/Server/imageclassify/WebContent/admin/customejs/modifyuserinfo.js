var usernameGlobal,sexGlobal,accuracyGlobal,integralGlobal;
$(function(){
	get_user_info();
});

function get_user_info(){
	Request = GetRequest();
	var user_id = Request['user_id'];
	$.post(
			'/ImageClassify/admin/getuserinfo',
			{"user_id": user_id},
			function(data, textStatus, xhr) {
				try{
	            res = $.parseJSON(data);
	            if (res.code == 200) {
	            	$("#id_avatar").attr("src", res.data.avatar_url);
	            	usernameGlobal = res.data.username;
	            	$("#username").val(usernameGlobal);
	            	sexGlobal = res.data.sex;
	            	if(sexGlobal == null){
	            		sexGlobal ="男";
	            	}
	            	$("#sex").val(sexGlobal);
	            	$("#phone_num").html(res.data.tel_num);
	            	integralGlobal = res.data.integral;
	            	if(integralGlobal == "null"){
	            		integralGlobal = "0";
	            	}
	            	$("#integral").val(integralGlobal);
	            	accuracyGlobal = res.data.accuracy;
	                if(accuracyGlobal== null || accuracyGlobal == "null"){
	                	accuracyGlobal = "0.0%";
	                }
	                $("#accuracy").val(accuracyGlobal);
	                set_button_click_event();
	            }else if(res.code == -1){
	            	alert(res.msg);
	            }else{
	            	alert(res.msg);
	            	window.location.href="/ImageClassify/admin/login.jsp";
	            }
	        }catch(err){
	            alert(err);
	            alert("查询数据出错");
	        } 
			});
	}

function set_button_click_event(){
	//提交并修改用户信息
	 $("#submit_modify_user_info").click(function(){
		 Request = GetRequest();
		 var user_id = Request['user_id'];
		 var username,sex,integral,accuracy;
		 username = $("#username").val();
		 if($("#username").val() == ""){
			  alert("用户名为空!");
			  return;
		 }
		 sex = $("#sex").val();
		 integral = $("#integral").val();
		 var patt = new RegExp('^\\d+$');
		 if(!patt.test(integral)){
			 alert("积分输入错误!");
			 return;
		 }
		 patt = new RegExp('^\\d+(\\.\\d+)?%$');
		 accuracy =  $("#accuracy").val();
		 if(accuracy=="" || !patt.test(accuracy)){
			 alert("准确度输入错误!");
			 return;
		 }
		 $.post(
					'/ImageClassify/admin/modifyuserinfo',
					{
						"user_id": user_id,
						"username":username,
						"sex":sex,
						"integral":integral,
						"accuracy":accuracy
					},
					
		 function(data, textStatus, xhr) {
				try{
	            res = $.parseJSON(data);
	            if (res.code == 200) {
	            	alert("修改用户信息成功！");
				}else if(res.code == -1){
	            	alert(res.msg);
	            }else{
	            	alert(res.msg);
	            	window.location.href="/ImageClassify/admin/login.jsp";
	            }
	        }catch(err){
	            alert(err);
	            alert("查询数据出错");
	        } 
	  });
	 });
	 //重置信息
	 $("#reset").click(function(){
		 $("#username").val(usernameGlobal);
		 $("#sex").val(sexGlobal);
		 $("#integral").val(integralGlobal);
		 $("#accuracy").val(accuracyGlobal);
	  });
}


function GetRequest() {
	var url = location.search; //获取url中"?"符后的字串
	var theRequest = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		if (str.indexOf("&") != -1) {
			strs = str.split("&");
			for (var i = 0; i < strs.length; i++) {
				theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
			}
		} else {
			theRequest[str.split("=")[0]] = unescape(str.split("=")[1]);
		}
	}
		return theRequest;
}