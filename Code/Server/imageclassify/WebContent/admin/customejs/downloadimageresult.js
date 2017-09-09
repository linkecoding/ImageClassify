var start = 1;
var page_size = 8; //每页数据条数
var page_num = 1;  //总页数
var cur_page = 1;    //当前页码
var isFirst = 1;
$(function() {
	get_download_images_num();
	get_download_images_history();
	set_button_click_event();
});

function get_download_images_num(){
	$.post('/ImageClassify/admin/exportresultlistamount', {},
			function(data, textStatus, xhr) {
		try {
			res = $.parseJSON(data);
			if (res.code == 200) {
				finished_image_amount_p = $("#finished_image_amount");
				finished_image_amount_p.html("");
				finished_image_amount_p.append('目前一共有'+res.data+'张图片被判定完成还未导出。');
				return;
			} else {
				alert(res.msg);
			}
		} catch (err) {
			alert(err);
			alert("查询数据出错");
		}
	});
}

function get_download_images_history() {
	$.post('/ImageClassify/admin/getdownloadimageshistory', {
		"start": start,
		"page_num": page_size
	},
			function(data, textStatus, xhr) {
		try {
			res = $.parseJSON(data);
			if (res.code == 200) {
				download_images_history_table = $("#download_images_history");
				download_images_history_table.html("");
				page_num = res.data.pages_num;
				$.each(res.data.list, function(index, el) {
					var date = new Date(el.export_time);
					download_images_history_table.append('<tr><td>' + 
							(index+start)+'</td><td>'+
							date.Format("yyyy年MM月dd日 hh:mm")+'</td><td>'+
                       		el.amount+'</td><td>'+
                       		'<input type="button" class="btn btn-success btn-xs" data-toggle="modal" data-target=".bs-history-modal-lg" onclick="preview_download_history_image(' + el.export_id + ')" value="预览"><span>  </span>'+
                       		'<input type="button" class="btn btn-success btn-xs" onclick="download_history_image_result(' + el.export_id + ')" value="导出"></td></tr>');
					if (isFirst == 1) {
						isFirst = 0;
						updatePage(); //更新页码
					} 
				});
				return;
			}else {
				alert(res.msg);
			}
		} catch (err) {
			alert(err);
			alert("查询数据出错");
		}
	});
}
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

function preview_download_history_image(export_id){
 
	$.post(
				'/ImageClassify/admin/exportorpreviewhistorylistresult',
				{
					"identity":"preview",
					"export_id":export_id
				},
				function(data, textStatus, xhr) {
					$("#id_tag_result_preview").html('');
					try{ 
		            res = $.parseJSON(data);
		            if (res.code == 200) { 
		            	$("#id_history_tag_result_preview").html('<pre>' + res.data.replace("\r\n", "</br>")+ '</pre>');
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
            get_download_images_history();
          }
        });
}

function download_history_image_result(export_id){
	window.location.href="/ImageClassify/admin/exporthistorylistresult?export_id=" + export_id;
}

function set_button_click_event(){
	$("#download_picture_result").click(function(){
		 $.post(
					'/ImageClassify/admin/exportresult',
					{},
					function(data, textStatus, xhr) {
						try{
			            res = $.parseJSON(data);
			            if (res.code == 200) { 
			            	alert(res.msg);
			            	window.location.href="/ImageClassify/admin/exporthistorylistresult?export_id=" + res.data;
			            }else{
			            	alert(res.msg);
			            }
			        }catch(err){
			            alert(err);
			            alert("查询数据出错");
			        } 
					});
	  }); 
	$("#preview_picture_result").click(function(){
		 $.post(
					'/ImageClassify/admin/exportresultlist',
					{},
					function(data, textStatus, xhr) {
						$("#id_tag_result_preview").html('');
						try{
			            res = $.parseJSON(data);
			            if (res.code == 200) {
			            	$("#id_tag_result_preview").html('<pre>' + res.data.replace("\r\n", "</br>")+ '</pre>');
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
	  });
}