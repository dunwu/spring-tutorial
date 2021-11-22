<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/jsp/taglibs.jsp" %>

<layout:override name="mainTitle">
    <h1>HelloWorld 示例</h1>
</layout:override>
<layout:override name="mainContent">
    <p><code>${message}</code></p>
</layout:override>
<jsp:include page="/WEB-INF/views/jsp/layout.jsp">
    <jsp:param value="hello" name="menuId" />
</jsp:include>
