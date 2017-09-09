var threshold_global,radio_global;
$(function(){
	get_system_config();
});

function get_system_config(){
	$.post(
			'/ImageClassify/admin/getsproconfig',{},
			function(data, textStatus, xhr) {
				try{
	            res = $.parseJSON(data);
	            if (res.code == 200) {
	            	var identify_frequency_marks,identify_time;
	            	$.each(res.data, function(index, el) {
	            		if ("threshold"==el.key){
	            			threshold_global = el.value; 
	            			$("#threshold_input").val(el.value);
	            		}else if ("identify_time"==el.key) {
	            			identify_time = el.value; 
	            		}else if ("identify_frequency_marks"==el.key) {
	            			identify_frequency_marks = el.value; 
	            		}
	     		  	});
	            	
	     			if ("early"==identify_frequency_marks && "more"==identify_time) {
	     				$("input[type=radio][name=radio][value=1]").attr("checked",true); 
	     				radio_global = 1; 
	     			}else if ("early"==identify_frequency_marks && "less"==identify_time) {
	     				$("input[type=radio][name=radio][value=2]").attr("checked",true); 
	     				radio_global = 2;
	     			}else if ("later"==identify_frequency_marks && "more"==identify_time) {
	     				$('input[type="radio"][name="radio"][value=3]').attr("checked",true); 
	     				radio_global = 3;
	     			}else{  
	     				//$("input[name='radio4']").eq(0).click();
	     				$('input[type="radio"][name="radio"][value="4"]').attr("checked",true); 
	     				//$("input[name='radio]").attr("checked",true); 
	     				radio_global = 4;
	     				 
	     			}
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

function modify_system_config(){
	var threshold = $("#threshold_input").val();
	if (""==threshold) {
		alert("请输入系统的标签判定阈值！");
	} 
	var radio_val = parseInt($('input:radio[name="radio"]:checked').val());
	var identify_frequency_marks,identify_time;
	switch(radio_val){
		case 1:  
			identify_frequency_marks = "early";
			identify_time = "more";
			break;
		case 2:
			identify_frequency_marks = "early";
			identify_time = "less";
			break;
		case 3:
			identify_frequency_marks = "later";
			identify_time = "more";
			break;
		case 4:
			identify_frequency_marks = "later";
			identify_time = "less";
			break;
		default:
			break;
	}
	$.post(
			'/ImageClassify/admin/modifyconfiginfos',{
				"threshold":threshold,
				"identify_time":identify_time,
				"identify_frequency_marks":identify_frequency_marks
			},
			function(data, textStatus, xhr) {
				try{
	            res = $.parseJSON(data);
	            if (res.code == 200) {
	            	alert("修改系统配置成功！");
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
	$("#submit").click(function(){
		 modify_system_config();
	  });
	//重置信息
	 $("#reset").click(function(){
		 $("#threshold_input").val(threshold_global);
		 $("input[type=radio][name=radio][value="+radio_global+"]").attr("checked",true); 
	  });
}