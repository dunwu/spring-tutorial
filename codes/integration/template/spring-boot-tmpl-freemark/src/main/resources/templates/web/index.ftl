<!DOCTYPE html>
<html>
<head>
  <title>FreeMarker 模板引擎</title>
</head>
<body>
<div>
  <h1>Hello FreeMarker</h1>
  <div>
    <p>导航：</p>
    <ul>
      <li><a href="/index">Home</a></li>
      <li><a href="/exception">Exception</a></li>
      <li><a href="/index2">Home Without CSS</a></li>
      <li><a href="/exception2">Exception Without CSS</a></li>
    </ul>
  </div>
  <p>Date: ${time?date}</p>
  <p>Time: ${time?time}</p>
  <p>Message: ${message}</p>
</div>
</body>
