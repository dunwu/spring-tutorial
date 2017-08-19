<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <title>spring-notes</title>
</head>

<body>
<h1>spring-notes</h1>
<p><%out.print("Server Ip：" + basePath);%></p>
<div>
  <a href="<%=basePath%>/aop/test">/aop/test</a><br>
</div>
</body>
</html>
