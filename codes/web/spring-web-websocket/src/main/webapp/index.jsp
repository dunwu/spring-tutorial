<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <title>WebSocket 实现聊天室</title>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</head>
<body>
<form action="msg/login" method="post">
  用户名:
  <select name="id">
    <option value="1">张三</option>
    <option value="2">李四</option>
  </select><br>
  密码:
  <input name="password" type="text" value="123456">
    <input type="submit" value="登录">
</form>
</body>
</html>
