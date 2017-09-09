var start = 1;
var page_size = 8; //每页数据条数
var page_num = 1;  //总页数
var cur_page = 1;    //当前页码
var isFirst = 1;
$(function(){
	get_upload_zip_list();
});

function get_upload_zip_list(){
	$.post(
			 '/ImageClassify/admin/getuploadziplist',
		{
			"start": start,
			"page_num": page_size
		},
		function(data, textStatus, xhr) {
			try{
            res = $.parseJSON(data);
            if (res.code == 200) { 
            	upload_zip_table = $("#upload_zip_list_table");
            	upload_zip_table.html("");
                page_num = res.data.pages_num; 
                $.each(res.data.list, function(index, el) {
                	var upzipStr;
                	var classifyStr;
                	if(el.isZip == "1"){
                		upzipStr = '<input type="button" class="btn btn-success btn-xs" onclick="unzip_picture(' + el.zip_id + ')" value="已解压" disabled="disabled"><span>  </span>';
                	}else{
                		upzipStr = '<input type="button" class="btn btn-success btn-xs" onclick="unzip_picture(' + el.zip_id + ')" value="解压"><span>  </span>';
                	}
                	if(el.isClassify == "1"){
                		classifyStr = '<input type="button" class="btn btn-success btn-xs" onclick="classify_picture(' + el.zip_id + ')" value="已识别" disabled="disabled"></td></tr>';
                	}else{
                		classifyStr = '<input type="button" class="btn btn-success btn-xs" onclick="classify_picture(' + el.zip_id + ')" value="识别"></td></tr>';
                	}
                	var date = new Date(el.upload_time);
                	upload_zip_table.append('<tr><td>' + (start+index) +'</td><td>'+
                    		el.old_zip_name+'</td><td>'+
                    		date.Format("yyyy年MM月dd日 hh:mm")+'</td><td>'+
                    		upzipStr+classifyStr);   		
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
	//格式化日期
	Date.prototype.Format = function (fmt) {
	  var o = {
	    "y+": this.getFullYear(),
	    "M+": this.getMonth() + 1,                 //月份
	    "d+": this.getDate(),                    //日
	    "h+": this.getHours(),                   //小时
	    "m+": this.getMinutes(),                 //分
	    "s+": this.getSeconds(),                 //秒
	    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
	    "S+": this.getMilliseconds()             //毫秒
	  };
	  for (var k in o) {
	    if (new RegExp("(" + k + ")").test(fmt)){
	      if(k == "y+"){
	        fmt = fmt.replace(RegExp.$1, ("" + o[k]).substr(4 - RegExp.$1.length));
	      }
	      else if(k=="S+"){
	        var lens = RegExp.$1.length;
	        lens = lens==1?3:lens;
	        fmt = fmt.replace(RegExp.$1, ("00" + o[k]).substr(("" + o[k]).length - 1,lens));
	      }
	      else{
	        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	      }
	    }
	  }
	  return fmt;
	}
}

/**
 * 解压图片
 * @param zip_id
 */
function unzip_picture(zip_id) {
	$.post(
		'/ImageClassify/admin/unzip',
		{"zip_id": zip_id},
		function(data, textStatus, xhr) {
			try{
            res = $.parseJSON(data);
            if (res.code == 200) {
            	alert("解压成功！");
            	get_upload_zip_list();
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
            get_upload_zip_list();
          }
        });
}

/**
 * 图片分类识别
 * @param zip_id
 */
function classify_picture(zip_id) {
	$.post(
		'/ImageClassify/admin/classify',
		{"zip_id": zip_id},
		function(data, textStatus, xhr) {
			try{
            res = $.parseJSON(data);
            if (res.code == 200) {
            	alert("识别成功！")
            	get_upload_zip_list();
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