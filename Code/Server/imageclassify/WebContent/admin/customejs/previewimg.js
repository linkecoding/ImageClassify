$(function(){
    Request = GetRequest();
    var id = Request['id'];
	show_preview_image(id);
});

//展示预览图片
function show_preview_image(id){
	$.post('/ImageClassify/admin/previewzipuploadbyuser',
		{
			"id": id
		},
		function(data, textStatus, xhr) {
			try{
                res = $.parseJSON(data);
                if (res.code == 200) {
                	preview_img_list = $("#preview_img_list");
                    preview_img_list.html("");
                    $.each(res.data, function(index, el) {
                        name = el.substring(el.lastIndexOf("/") + 1);
                        preview_img_list.append('<div class="col-lg-3"><div class="panel panel-primary"><div class="panel-heading">'
                            + name + '</div><div class="panel-body"><img src="' + el + '" class = "img1"/></div></div></div>'
                            )});
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