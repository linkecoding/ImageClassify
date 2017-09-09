var start = 1;
var page_size = 8; //每页数据条数
var page_num = 1;  //总页数
var cur_page = 1;    //当前页码
var isFirst = 1;
$(function(){
	get_unfrozen_users();
});

function get_unfrozen_users(){
	$.post(
			 '/ImageClassify/admin/unfrozenuserlist',
		{
			"start": start,
			"page_num": page_size
		},
		function(data, textStatus, xhr) {
			try{
           res = $.parseJSON(data);
           if (res.code == 200) {
        	   unfrozen_user_table = $("#unfrozen_user_table");
        	   unfrozen_user_table.html("");
             page_num = res.data.pages_num;
               $.each(res.data.list, function(index, el) {
            	   var accuracy;
            	   if(el.accuracy == "null" || el.accuracy == "undefined"){
            		   accuracy = "0.0%";
            	   }else{
            		   accuracy = el.accuracy;  
            	   }        		  
	    	   		unfrozen_user_table.append('<tr><td>' + (start+index) +'</td><td>'+
	               		el.username+'</td><td>'+
	               		el.tel_num+'</td><td>'+
	               		el.integral+'</td><td>'+
	               		accuracy+'</td><td>'+
	               		'<input type="button" class="btn btn-success btn-xs" onclick="frozenuser(' + el.user_id + ')" value="冻结"><span>  </span>'+ 
	               		'<input type="button" class="btn btn-success btn-xs" onclick="modifyuserinfo(' + el.user_id + ')" value="修改"></td></tr>');             	
               });
               if (isFirst == 1) {
                    isFirst = 0;
                    updatePage(); //更新页码
                }
               return;
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

function updatePage(){
        $("#pagination").pagination({
          currentPage: cur_page,
          totalPage: page_num,
          isShow: true,
          count: 5,
          homePageText: "首页",
          endPageText: "尾页",
          prevPageText: "上一页",
          nextPageText: "下一页",
          callback: function(current) {
            cur_page = current;
            start = (current-1) * page_size + 1; 
            get_unfrozen_users();
          }
        });
}

function frozenuser(user_id) {
	$.post( 
		'/ImageClassify/admin/frozenuser',
		{"user_id": user_id},
		function(data, textStatus, xhr) {
			try{
			 
            res = $.parseJSON(data);
            if (res.code == 200) {
            	   get_unfrozen_users();
                 return;
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

function modifyuserinfo(user_id){
	//跳转到修改用户信息界面
	window.location.href="modifyuserinfo.jsp?user_id="+user_id; 
}
