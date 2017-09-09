$(function(){
	//绘制第一个饼图
	draw_img_amount_all_task_pie();
	//绘制第二个饼图
	draw_all_task_finished_pie();
	//绘制第三个饼
	draw_all_user_sex_pie();
});
//绘制饼图
function drawTaskAssignPie(divId, data){
  $.plot($(divId), data, {
    series : {
    pie:{   
        show: true,  
        radius: 1, //半径  
        label: {  
          show: true,  
          radius: 2/3,  
          formatter: function(label, series){  
              return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'+label+'<br/>'+Math.round(series.percent)+'%</div>';  
        },  
        threshold: 0.03  //这个值小于0.03，也就是3%时，label就会隐藏  
      }
    }
    },
    legend: {  
      show: false  
    }  
  });
}

//获取每个数量图片的任务
function draw_img_amount_all_task_pie(){
	$.post(
		'/ImageClassify/admin/getimgamountdata',
		function(data, textStatus, xhr) {
		try{
            res = $.parseJSON(data);
            if (res.code == 200) {
            	img_amount_all_task_data = 
					[{label :"10张", data : res.data.amount10}, 
					{label : "20张", data : res.data.amount20},
					{label : "30张", data : res.data.amount30}
					];
				//绘图
				drawTaskAssignPie("#id_img_amount_all_task_pie", img_amount_all_task_data);
				//填充图片总数量
				$("#id_all_task_amount").text("总任务量 " + res.data.all_task_amount);
				$("#img_amount_div").append('<span class="col-sm-12" style="margin-top: 10px;text-align: center;">每种数量的图片任务分配</span>');
                return;
            }else{
            	alert(res.msg);
            }
        }catch(err){
            alert(err);
            alert("查询数据出错");
        } 
	});
}

//绘制任务判定情况饼图
function draw_all_task_finished_pie(){
	$.post(
		'/ImageClassify/admin/getalltaskfinished',
		function(data, textStatus, xhr) {
			try{
            res = $.parseJSON(data);
            if (res.code == 200) {
            	img_task_finish_data = 
					[{label :"已判断", data : res.data.finished_img}, 
					{label : "未判定", data : res.data.unfinished_img}
					];
				//绘图
				drawTaskAssignPie("#id_all_task_finished_pie", img_task_finish_data);
				//填充图片总数量
				$("#id_all_img_amount").text("总图片数 " + res.data.all_img_amount);
				$("#task_amount_div").append('<span class="col-sm-12" style="margin-top: 10px; text-align: center;">已完成与未完成任务</span>');
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

//绘制所有志愿者性别分布图
function draw_all_user_sex_pie(){
	$.post(
		'/ImageClassify/admin/getallusersex',
		function(data, textStatus, xhr) {
			try{
            res = $.parseJSON(data);
            if (res.code == 200) {
            	user_sex_data = 
				[{label :"男", data : res.data.man_user}, 
				{label : "女", data : res.data.woman_user},
				{label : "未知", data : res.data.unknow_user}
				];
				//绘图
				drawTaskAssignPie("#id_all_user_sex_pie", user_sex_data);
				//填充图片总数量
				$("#id_all_user_amount").text("总志愿者数 " + res.data.all_user_amount);
				$("#user_amount_div").append(' <span class="col-sm-12" style="margin-top: 10px; text-align: center;">志愿者性别分布</span>');
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