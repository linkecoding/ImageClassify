$(function() {
    set_submit_button_click_event();
    set_reset_button_click_event();
});

function set_submit_button_click_event() {
    $("#modify_admin_password").click(function(){
        var old_passwordInput,new_passwordInput,confirmPasswordInput;
    old_passwordInput = $("#old_password");
    new_passwordInput = $("#new_password");
    confirmPasswordInput = $("#confirm_password");
    if(old_passwordInput.val() == "" ){
        alert("旧密码不能为空！");
        return;
    }
    if (new_passwordInput.val() == "") {
        alert("新密码不能为空！");
        return;
    };
    if (confirmPasswordInput.val() == "") {
        alert("确认密码不能为空！")
        return;
    };
    if (confirmPasswordInput.val() != new_passwordInput.val()) {
        alert("确认密码与新密码不一致！");
        return;
    };
    $.post(
        '/ImageClassify/admin/modifyadminpassword',
        {
            "old_password": old_passwordInput.val(),
            "new_password": new_passwordInput.val()
    },
        function(data, textStatus, xhr) {
            try{
            res = $.parseJSON(data);
            if (res.code == 200) {
                alert(res.msg); 
                window.location.href="/ImageClassify/admin/logout";
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

function set_reset_button_click_event(){
     $("#reset").click(function(){
       $("#old_password").val("");
       $("#new_password").val("");
       $("#confirm_password").val("");
     });
}