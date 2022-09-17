<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html>
<head>
  <title>FreeMarker 模板引擎</title>
  <#assign home><@spring.url relativeUrl="/"/></#assign>
  <#assign bootstrap><@spring.url relativeUrl="/css/bootstrap.min.css"/></#assign>
  <link rel="stylesheet" href="${bootstrap}" />
</head>
<body>
<div class="container">
  <div class="row clearfix">
    <div class="col-md-12 column">
      <ul class="breadcrumb">
        <li class="active"><a href="/index">Home</a></li>
        <li><a href="/exception">Exception</a></li>
        <li><a href="/index2">Home Without CSS</a></li>
        <li><a href="/exception2">Exception Without CSS</a></li>
      </ul>
      <div class="jumbotron">
        <p>Date: ${time?date}</p>
        <p>Time: ${time?time}</p>
        <p>Message: ${message}</p>
        <p>
          <a class="btn btn-primary btn-large" href="https://github.com/dunwu/spring-boot-tutorial">Learn more</a>
        </p>
      </div>
    </div>
  </div>
</div>
</body>
