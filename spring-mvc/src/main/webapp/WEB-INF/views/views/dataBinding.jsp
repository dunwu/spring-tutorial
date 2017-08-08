<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Data Binding with URI Template Variables</title>
	<link href="<c:url value="/resources/form.css" />" rel="stylesheet"  type="text/css" />		
</head>
<body>
<div class="success">
	<h3>javaBean.foo: ${javaBean.foo}</h3>
	<h3>javaBean.fruit: ${javaBean.fruit}</h3>
</div>
</body>
</html>