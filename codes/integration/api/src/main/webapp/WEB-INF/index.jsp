<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta charset="UTF-8">
  <title>spring-notes</title>
  <link rel="icon" type="image/png" href="swagger-ui/images/favicon-32x32.png" sizes="32x32"/>
  <link rel="icon" type="image/png" href="swagger-ui/images/favicon-16x16.png" sizes="16x16"/>
  <link href='swagger-ui/css/typography.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='swagger-ui/css/reset.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='swagger-ui/css/screen.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='swagger-ui/css/reset.css' media='print' rel='stylesheet' type='text/css'/>
  <link href='swagger-ui/css/print.css' media='print' rel='stylesheet' type='text/css'/>
  <script src='swagger-ui/lib/jquery-1.8.0.min.js' type='text/javascript'></script>
  <script src='swagger-ui/lib/jquery.slideto.min.js' type='text/javascript'></script>
  <script src='swagger-ui/lib/jquery.wiggle.min.js' type='text/javascript'></script>
  <script src='swagger-ui/lib/jquery.ba-bbq.min.js' type='text/javascript'></script>
  <script src='swagger-ui/lib/handlebars-2.0.0.js' type='text/javascript'></script>
  <script src='swagger-ui/lib/underscore-min.js' type='text/javascript'></script>
  <script src='swagger-ui/lib/backbone-min.js' type='text/javascript'></script>
  <script src='swagger-ui/swagger-ui.js' type='text/javascript'></script>
  <script src='swagger-ui/lib/highlight.7.3.pack.js' type='text/javascript'></script>
  <script src='swagger-ui/lib/marked.js' type='text/javascript'></script>
  <script src='swagger-ui/lib/swagger-oauth.js' type='text/javascript'></script>

  <!-- i18n -->
  <script src='swagger-ui/lang/translator.js' type='text/javascript'></script>
  <script src='swagger-ui/lang/zh-cn.js' type='text/javascript'></script>
</head>

<body class="swagger-section">
<div id='header'>
  <div class="swagger-ui-wrap">
    <a id="logo" href="https://github.com/dunwu/spring-notes">spring-notes</a>
  </div>
</div>

<div id="message-bar" class="swagger-ui-wrap" data-sw-translate>&nbsp;</div>
<div id="swagger-ui-container" class="swagger-ui-wrap">
  <h2>示例</h2>
  <hr><br>
  <blockquote style="background-color:#F7F7F7;padding: 20px">
    本示例展示如何使用 spring mvc + swagger-ui 动态生成 API 平台。
  </blockquote>
  <br>
  <strong><a href="<%=basePath%>swagger.html">点击访问API文档</a></strong>
</div>
</body>
</html>
