$(function() {
	show_all_category();
	set_button_click_event();
});

function show_all_category() {
	$.post('/ImageClassify/admin/getallcategory',
			{},
			function(data, textStatus, xhr) {
				try {
					res = $.parseJSON(data);
					if (res.code == 200) {
						all_category_div = $("#all_category_div");
						all_category_div.html("");
						$.each(res.data, function(index, el) {
							all_category_div.append('<button style="margin-bottom:5px;margin-left:5px;" class="btn btn-white" onclick="download_category_zip('
									+ el.category.category_id
									+ ','
									+ el.img_amount_of_category
									+ ')" type="button">'
									+ el.category.category_name
									+ '</button>');
							});
						return;
					} else if (res.code == -1) {
						alert(res.msg);
					} else {
						alert(res.msg);
						window.location.href = "/ImageClassify/admin/login.jsp";
					}
				} catch (err) {
					alert(err);
					alert("查询数据出错");
				}
			});
}

function set_button_click_event() {
	$("#add_category").click(function() {
		category_input = $("#category_input");
		if (category_input.val() == "") {
			alert("分类输入为空！");
			return;
		}
		// 网络请求添加分类
		$.post('/ImageClassify/admin/addcategory', {
			"category" : category_input.val()
		}, function(data, textStatus, xhr) {
			try {
				res = $.parseJSON(data);
				if (res.code == 200) {
					category_input.val("");
					show_all_category();
					return;
				} else if (res.code == -1) {
					alert(res.msg);
				} else {
					alert(res.msg);
					window.location.href = "/ImageClassify/admin/login.jsp";
				}
			} catch (err) {
				alert(err);
				alert("查询数据出错");
			}
		});
	});
}

function download_category_zip(category_id, img_amount_of_category) {
	if (img_amount_of_category != "0") {
		window.open('/ImageClassify/admin/getzipbycategory?category_id='
				+ category_id);
	} else {
		alert("该分类图片为空！");
	}
}