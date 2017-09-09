<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<form name="form1" METHOD="POST" ACTION="/ImageClassify/contributeimg" ENCTYPE="multipart/form-data">  
	oauth:<input type="text" name="oauth_token" id = "oauth_token"/>
		  <input name="file" type="file" id="file" ><br>
		  <input type="submit" value="提交2">  
</form>
</body>
</html>