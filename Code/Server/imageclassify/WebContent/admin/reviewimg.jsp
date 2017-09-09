<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Pragma" contect="no-cache"> 
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="images/favicon.png" type="image/png">
    <title>审核图片</title>
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
            <li class="nav-parent nav-active active">
              <a href="#">
                <i class="fa fa-tasks"></i>
                <span>任务管理</span></a>
              <ul class="children"  style="display:block;">
                <li>
                  <a href="taskassign.jsp">
                    <i class="fa fa-caret-right"></i>任务分配情况</a>
                </li>
                <li class="active">
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
            <i class="glyphicon glyphicon-user"></i>审核图片
            <span>审核由志愿者贡献上传的图片</span>
            </h2>
        </div>
        <div class="contentpanel">
          <table class="table table-success mb30">
              <thead>
                <tr>
                  <th>序号</th>
                  <th>用户名</th>
                  <th>手机号</th>
                  <th>上传时间</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody id="contribute_img_table">
                 
              </tbody>
          </table>
          <div>
              <div id="pagination" class="page fl"></div>
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
    <script src="js/custom.js"></script>
    <script src="customejs/reviewimg.js"></script>
    <script src="js/jquery.pagination.min.js"></script>
  </body>
</html>