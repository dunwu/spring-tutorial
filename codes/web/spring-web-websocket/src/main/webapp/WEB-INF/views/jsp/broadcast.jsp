<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath =
            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title></title>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
  <script type="text/javascript">
    var path = '<%=basePath%>';

      function broadcast() {
        $.ajax({
          url: path + 'msg/broadcast',
          type: "post",
          data: {text: $("#msg").val()},
          dataType: "json",
          success: function (data) {
            alert("发送成功");
          }
        });
      }
    </script>
</head>
<body>
发送广播
<textarea style="width:100%;height:300px;" id="msg"></textarea>
<input type="button" value="发送" onclick="broadcast()">
</body>
</html>
