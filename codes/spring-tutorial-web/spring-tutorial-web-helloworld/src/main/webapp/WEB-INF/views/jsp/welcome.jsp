<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/jsp/taglibs.jsp" %>

<layout:override name="mainTitle">
    <h1>Welcome</h1>
</layout:override>
<layout:override name="mainContent">
    <h2>Welcome to use spring-tutorial.</h2>
    <h3>Current time is: ${time}</h3>
</layout:override>
<jsp:include page="/WEB-INF/views/jsp/layout.jsp">
    <jsp:param value="welcome" name="menuId" />
</jsp:include>
