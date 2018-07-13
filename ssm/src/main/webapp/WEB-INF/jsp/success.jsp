<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Registration Confirmation Page</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
<div class="generic-container">
	<div class="alert alert-success lead">
	666
    	<%-- ${success} --%>
	</div>
	<p>
  <a class="btn btn-primary btn-large" href="<c:url value='/userlogin' />">登陆</a>
  <a class="btn btn-primary btn-large" href="<c:url value='/newuser' />">注册</a>
</p>
	<span class="well floatRight">
		前往<a href="<c:url value='/userlist' />">用户列表</a>
		前往<a href="<c:url value='/booklist' />">图书列表</a>
		前往<a href="<c:url value='/borrowlist' />">借书列表</a>
	</span>
</div>
</body>

</html>