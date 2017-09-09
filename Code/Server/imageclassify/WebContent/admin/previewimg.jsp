<!DOCTYPE html>
<html lang="en">
  
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="images/favicon.png" type="image/png">
    <title>图片预览</title>
    <link href="css/style.default.css" rel="stylesheet">
    <style>
    	.img1{
    		width:100%;
    		height:200px;
    	}
    </style>
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
      <div class="mainpanel" style="margin-left:0px;">
        <div class="headerbar">
          <a class="menutoggle">
            <i class="fa fa-bars"></i>
          </a>
          <form class="searchform" action="index.jsp" method="post">
            <input type="text" class="form-control" name="keyword" placeholder="查找..." /></form>
          <div class="header-right">
            <ul class="headermenu">
              <li>
                <div class="btn-group">
                  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <img src="images/photos/loggeduser.png" alt="" />小红
                    <span class="caret"></span></button>
                  <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
                    <li>
                      <a href="#">
                        <i class="glyphicon glyphicon-cog"></i>账号设置</a>
                    </li>
                    <li>
                      <a href="signin.jsp">
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
            <i class="fa fa-home"></i>图片预览
            <span>预览压缩包内的图片</span></h2>
        </div>
        <div class="contentpanel">
          <div class="row" id="preview_img_list">
          	
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
    <script src="customejs/previewimg.js"></script>
  </body>
</html>