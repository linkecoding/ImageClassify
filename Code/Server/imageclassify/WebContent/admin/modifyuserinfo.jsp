<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"> 
  <head> 
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">  
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="images/favicon.png" type="image/png">
    <title>修改用户信息</title>
    <link href="css/style.default.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]--></head>
  
  <body>
    <!-- Preloader -->
    <div id="preloader">
      <div id="status">
        <i class="fa fa-spinner fa-spin"></i>
      </div>
    </div>
    <section>
      <div class="leftpanel">
        <div class="logopanel">
          <h1>
            <span></span>管理中心
            <span></span></h1>
        </div>
        <!-- logopanel -->
        <div class="leftpanelinner">
          <ul class="nav nav-pills nav-stacked nav-bracket">
            <!-- 首页选项卡 -->
            <li>
              <a href="index.jsp">
                <i class="fa fa-home"></i>
                <span>首页</span></a>
            </li>
            <!-- 管理用户信息选项卡 -->
            <li class="nav-parent">
              <a href="#">
                <span class="glyphicon glyphicon-user"></span>
                <span>用户管理</span></a>
              <ul class="children">
               	<li>
                  	<a href="allusers.jsp">
                    <i class="fa fa-caret-right"></i>所有用户</a>
                </li>
                <li>
                  	<a href="unfrozenuser.jsp">
                    <i class="fa fa-caret-right"></i>未冻结用户</a>
                </li>
                <li>
                  	<a href="frozenuser.jsp">
                    <i class="fa fa-caret-right"></i>已冻结用户</a>
                </li>
              </ul>
            </li>
            <li class="nav-parent">
              <a href="#">
                <i class="fa fa-file-image-o"></i>
                <span>图片管理</span></a>
              <ul class="children">
                <li>
                  <a href="uploadimg.jsp">
                    <i class="fa fa-caret-right"></i>上传图片</a>
                </li>
                <li>
                  <a href="uploadziplist.jsp">
                    <i class="fa fa-caret-right"></i>图片压缩包列表</a>
                </li>
                <li>
                  <a href="downloadimageresult.jsp">
                    <i class="fa fa-caret-right"></i>导出图片标签化结果</a>
                </li>
              </ul>
            </li>
            <li class="nav-parent">
              <a href="#">
                <i class="fa fa-tasks"></i>
                <span>任务管理</span></a>
              <ul class="children">
                <li>
                  <a href="taskassign.jsp">
                    <i class="fa fa-caret-right"></i>任务分配情况</a>
                </li>
              </ul>
              <ul class="children">
                <li>
                  <a href="reviewimg.jsp">
                    <i class="fa fa-caret-right"></i>志愿者图片审核</a>
                </li>
              </ul>
            </li>
            <li class="nav-parent">
              <a href="#">
                <i class="fa fa-tasks"></i>
                <span>分类管理</span></a>
              <ul class="children">
                <li>
                  <a href="addcategory.jsp">
                    <i class="fa fa-caret-right"></i>添加类别</a>
                </li>
              </ul>
            </li>
            <li class="nav-parent">
              <a href="#">
                <i class="fa fa-lock"></i>
                <span>账号设置</span></a>
              <ul class="children"">
                <li>
                  <a href="modifyadminpassword.jsp">
                    <i class="fa fa-caret-right"></i>修改密码</a>
                </li>
              </ul>
            </li>
             <li class="nav-parent">
              <a href="#">
                <i class="fa fa-tasks"></i>
                <span>系统配置</span></a>
              <ul class="children">
                <li>
                  <a href="systemsettings.jsp">
                    <i class="fa fa-caret-right"></i>系统配置</a>
                </li>
              </ul>
            </li>
            <li class="nav-parent">
              <a href="#">
                <i class="fa fa-tasks"></i>
                <span>机器学习训练</span></a>
              <ul class="children">
                <li>
                  <a href="http://101.132.35.65:6006" target="_blank">
                    <i class="fa fa-caret-right"></i>查看训练过程</a>
                </li>
              </ul>
            </li>
          </ul>
        </div>
        <!-- infosummary --></div>
      <!-- leftpanelinner --></div>
      <!-- leftpanel -->
      <div class="mainpanel">
        <div class="headerbar">
          <a class="menutoggle">
            <i class="fa fa-bars"></i>
          </a>
          <form class="searchform" action="index.html" method="post">
            <input type="text" class="form-control" name="keyword" placeholder="查找..." /></form>
          <div class="header-right">
            <ul class="headermenu">
              <li>
                <div class="btn-group">
                  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <img src="images/photos/loggeduser.png" alt="" /><%=session.getAttribute("username") %>
                    <span class="caret"></span></button>
                  <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
                    <li>
                      <a href="modifyadminpassword.jsp">
                        <i class="glyphicon glyphicon-cog"></i>账号设置</a>
                    </li>
                    <li>
                      <a href="/ImageClassify/admin/logout">
                        <i class="glyphicon glyphicon-log-out"></i>退出登录</a>
                    </li>
                  </ul>
                </div>
              </li>
            </ul>
          </div>
          <!-- header-right --></div>
        <!-- headerbar -->
        <div class="pageheader">
          <h2>
            <i class="fa fa-user"></i>修改用户信息
            <span></span></h2>
        </div>
        <div class="contentpanel">
          <div class="panel panel-default">
              <div class="panel-heading">
                <h4 class="panel-title">修改用户信息</h4>
                <p>管理员可修改用户的用户名、性别、积分信息</p>
              </div>
              <div class="panel-body">
              <div class="form-group has-error">
                  <label class="col-sm-4 control-label"></label>
                  <div class="col-sm-3">
                  <img src="images/favicon.png" width="40%" height="40%" id="id_avatar">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">用户名<span class="asterisk"></span></label>
                  <div class="col-sm-6">
                    <input id="username" type="text" name="name" class="form-control" placeholder="请输入用户名" required="">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">性别</label>
                  <div class="col-sm-6">
                    <select id="sex" class="select2 select2-offscreen" required="" data-placeholder="选择一项" tabindex="-1" title="">
                      <option value=""></option>
                      <option value="男">男</option>
                      <option value="女">女</option>
                    </select>
                    <label class="error" for="fruits"></label>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">手机号<span class="asterisk"></span></label>
                  <div id="phone_num" class="col-sm-6">
                    15161161067
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">积分<span class="asterisk"></span></label>
                  <div class="col-sm-6">
                    <input id="integral" type="text" name="name" class="form-control" placeholder="请输入积分" required="">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">准确率<span class="asterisk"></span></label>
                  <div class="col-sm-6">
                    <input id="accuracy" type="text" name="name" class="form-control" placeholder="请输入准确率" required="">
                  </div>
                </div>
              </div><!-- panel-body -->
              <div class="panel-footer">
                <div class="row">
                  <div class="col-sm-9 col-sm-offset-3">
                    <button id="submit_modify_user_info"class="btn btn-primary">提交</button>
                    <button id="reset" type="reset" class="btn btn-default">重置</button>
                  </div>
                </div>
              </div>
          </div>
        </div>
      </div>
      <!-- mainpanel -->
      <!-- rightpanel --></section>
    <script src="js/jquery-1.11.1.min.js"></script>
    <script src="js/jquery-migrate-1.2.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/modernizr.min.js"></script>
    <script src="js/jquery.sparkline.min.js"></script>
    <script src="js/toggles.min.js"></script>
    <script src="js/retina.min.js"></script>
    <script src="js/jquery.cookies.js"></script>
    <script src="js/jquery.sparkline.min.js"></script>
    <script src="js/custom.js"></script>
    
    <script src="js/select2.min.js"></script>
    <script src="js/jquery.validate.min.js"></script>
    <script src="customejs/modifyuserinfo.js"></script>
    <script>
    jQuery(document).ready(function(){
        "use strict";
        // Select2
      jQuery(".select2").select2({
        width: '100%',
        minimumResultsForSearch: -1
      });
      
      // Basic Form
      jQuery("#basicForm").validate({
        highlight: function(element) {
          jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function(element) {
          jQuery(element).closest('.form-group').removeClass('has-error');
        }
      });
      
      // Error Message In One Container
      jQuery("#basicForm2").validate({
       errorLabelContainer: jQuery("#basicForm2 div.error")
      });
      
      // With Checkboxes and Radio Buttons
      jQuery("#basicForm3").validate({
        highlight: function(element) {
          jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function(element) {
          jQuery(element).closest('.form-group').removeClass('has-error');
        }
      });
      
      // Validation with select boxes
      jQuery("#basicForm4").validate({
        highlight: function(element) {
          jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function(element) {
          jQuery(element).closest('.form-group').removeClass('has-error');
        }
      });
      
      
    });
    </script>
</body>
</html>