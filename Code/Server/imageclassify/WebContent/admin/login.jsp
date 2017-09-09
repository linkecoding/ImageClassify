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
    <title>管理员登录</title>
    <link href="css/style.default.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]--></head>
  
  <body class="signin">
    <section>
      <div class="signinpanel">
        <div class="row">
          <div class="col-md-7">
            <div class="signin-info">
              <div class="logopanel">
                <h1>
                  <span>[</span>图片认知分类系统
                  <span>]</span></h1>
              </div>
              <!-- logopanel -->
              <div class="mb20"></div>
              <h5>
                <strong>欢迎登陆图片认知分类系统管理员端</strong></h5>
              <ul>
                <li>
                  <i class="fa fa-arrow-circle-o-right mr5"></i>你可以管理志愿者</li>
                <li>
                  <i class="fa fa-arrow-circle-o-right mr5"></i>你可以上传图片任务</li>
                <li>
                  <i class="fa fa-arrow-circle-o-right mr5"></i>你可以查看任务完成情况</li>
                <li>
                  <i class="fa fa-arrow-circle-o-right mr5"></i>你可以处理异常情况</li>
                 <li>
                  <i class="fa fa-arrow-circle-o-right mr5"></i>你可以审核志愿者贡献的图片</li>
                <li>
                  <i class="fa fa-arrow-circle-o-right mr5"></i>你可以查看机器学习训练过程</li>
                <li>
                  <i class="fa fa-arrow-circle-o-right mr5"></i>等等...</li>
              </ul>
            </div>
            <!-- signin0-info --></div>
          <!-- col-sm-7 -->
          <div class="col-md-5">
            <form method="post" action="${pageContext.request.contextPath}/admin/login.do">
              <h4 class="nomargin">登录</h4>
              <p class="mt5 mb20">请使用管理员账号登录</p>
              <input type="text" class="form-control uname" name ="username" placeholder="用户名" />
              <input type="password" class="form-control pword" name="password" placeholder="密码" />
              <button class="btn btn-success btn-block">登录</button></form>
          </div>
          <!-- col-sm-5 --></div>
        <!-- row -->
        <div class="signup-footer">
          <div class="pull-left">&copy; 2017. All Rights Reserved</div>
          <div class="pull-right">Created By:
            <a href="#">吃藕队</a></div>
        </div>
      </div>
      <!-- signin --></section>
    <script src="js/jquery-1.11.1.min.js"></script>
    <script src="js/jquery-migrate-1.2.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/modernizr.min.js"></script>
    <script src="js/jquery.sparkline.min.js"></script>
    <script src="js/jquery.cookies.js"></script>
    <script src="js/toggles.min.js"></script>
    <script src="js/retina.min.js"></script>
    <script src="js/custom.js"></script>
  </body>
</html>