<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
    	
    	 <form action = "${pageContext.request.contextPath}/user/modifyinfo" method="post" id="modify" enctype="multipart/form-data">
    			  <input type="text" name="oauth_token"><br>
    			  <input type="text" name="sex"><br>
    			  <input type="text" name="username"><br>
    			  <input type="file" name="avatar" id="avatar"/> 
   		    	  <input type="submit" value="提交" /> 
   		 </form>

</body>
</html>