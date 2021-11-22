<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/jsp/taglibs.jsp" %>

<layout:override name="mainTitle">
    <h1>日志示例</h1>
</layout:override>
<layout:override name="mainContent">
    <p>进入本页面后，后台应用会自动打印日志。</p>
    <p>打印信息：${message}</p>
</layout:override>
<jsp:include page="/WEB-INF/views/jsp/layout.jsp">
    <jsp:param value="log" name="menuId" />
</jsp:include>
