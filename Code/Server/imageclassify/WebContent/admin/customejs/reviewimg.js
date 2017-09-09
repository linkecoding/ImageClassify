  var start = 1;
  var page_size = 8; //每页数据条数
  var page_num = 1;  //总页数
  var cur_page = 1;    //当前页码
  var isFirst = 1;
  $(function(){
  	get_contribute_img_list();
  });

  function get_contribute_img_list(){
  	$.post(
  			 '/ImageClassify/admin/getcontributeuploadziplist',
  		{
  			"start": start,
  			"page_num": page_size
  		},
  		function(data, textStatus, xhr) {
  			try{
             res = $.parseJSON(data);
             if (res.code == 200) {
          	   contribute_img_table = $("#contribute_img_table");
          	   contribute_img_table.html("");
               page_num = res.data.pages_num;
                 $.each(res.data.list, function(index, el) {
            	   		contribute_img_table.append('<tr><td>' + (start+index) +'</td><td>'+
                     		el.username+'</td><td>'+
                     		el.tel_num+'</td><td>'+
                     		el.upload_img_time+'</td><td>'+
                     		'<input type="button" class="btn btn-success btn-xs" onclick="preview_img_list(' + el.id + ')" value="预览"><span>  </span>'
                        +'<input type="button" class="btn btn-success btn-xs" onclick="review_pass(' + el.id + ')" value="通过"><span>  </span>'
                         +'<input type="button" class="btn btn-success btn-xs" onclick="review_not_pass(' + el.id + ')" value="不通过"></td></tr>');             	
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
              get_contribute_img_list();
            }
          });
  }

  /**
   * 预览贡献的图片列表
   * @param  {[type]} user_id [description]
   * @return {[type]}         [description]
   */
  function preview_img_list(id) {
  	window.open("/ImageClassify/admin/previewimg.jsp?id=" + id);
  }

  /**
   * 审核通过
   * @param  {[type]} user_id [description]
   * @return {[type]}         [description]
   */
  function review_pass(id){
    $.post(
      '/ImageClassify/admin/reviewsuccess',
      {
        'id': id
      },
      function(data, textStatus, xhr) {
          try{
              res = $.parseJSON(data);
              if (res.code == 200) {
                  //刷新列表
                  get_contribute_img_list();
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


  /**
   * 审核不通过
   * @param  {[type]} user_id [description]
   * @return {[type]}         [description]
   */
  function review_not_pass(id){
    $.post(
      '/ImageClassify/admin/reviewfail',
      {
        'id': id
      },
      function(data, textStatus, xhr) {
          try{
              res = $.parseJSON(data);
              if (res.code == 200) {
                  //刷新列表
                  get_contribute_img_list();
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