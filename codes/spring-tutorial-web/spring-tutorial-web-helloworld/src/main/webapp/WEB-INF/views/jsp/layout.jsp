<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/jsp/taglibs.jsp" %>

<!doctype html>
<html lang="en">
<head>
    <title>Spring Tutorial</title>

    <!-- required meta tags -->
    <meta charset="utf-8">
    <meta name="description" content="Spring Tutorial">
    <meta name="author" content="Zhang Peng">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <!-- required css -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/css/bootstrap.min.css" />
    <layout:block name="css"></layout:block>
</head>
<style>
    .navbar-brand {
        font-size: 28px;
    }

    .nav-link {
        font-size: 18px;
        color: #399ab2;
    }

    .nav-link.active {
        color: #FE4165;
    }

    .foot-content {
        font-size: 22px;
        color: slategrey;
    }
</style>

<body>

<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <a class="navbar-brand" href="${ctx}">Spring Tutorial</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#headerNavbar"
            aria-controls="headerNavbar" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="headerNavbar">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="https://github.com/dunwu/spring-tutorial">Github</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="https://dunwu.github.io/spring-tutorial/">Docs</a>
            </li>
        </ul>
    </div>
</nav>

<!-- 页面首部 -->
<div class="container-fluid">
    <div class="row">
        <nav class="col-md-2 d-none d-md-block bg-light sidebar">
            <div class="sidebar-sticky" style="padding: 10px">
                <h5 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                    <span style="font-size: 24px;">示例清单</span>
                </h5>
                <ul class="nav flex-column mb-2">
                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${param.menuId eq 'hello'}">
                                <a class="nav-link active" href="${ctx}/hello?name=zhangsan">HelloWorld 示例</a>
                            </c:when>
                            <c:otherwise>
                                <a class="nav-link" href="${ctx}/hello?name=zhangsan">HelloWorld 示例</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${param.menuId eq 'log'}">
                                <a class="nav-link active" href="${ctx}/log">日志示例</a>
                            </c:when>
                            <c:otherwise>
                                <a class="nav-link" href="${ctx}/log">日志示例</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </div>
        </nav>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
            <div style="min-height: 650px">
                <div
                        class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <layout:block name="mainTitle"></layout:block>
                </div>
                <layout:block name="mainContent"></layout:block>
            </div>

            <footer class="footer mt-auto py-3 text-center">
                <div class="container">
          <span class="foot-content">
            <a href="https://dunwu.github.io/spring-tutorial/">Spring Tutorial</a>
            by <a href="https://dunwu.github.io/dunwu">@Zhang Peng</a>.
          </span>
                </div>
            </footer>
        </main>
    </div>
</div>

<!-- required javascript -->
<!-- 引用的脚本放在末尾以提速 -->
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.4.1/jquery.slim.min.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
<layout:block name="script"></layout:block>
</body>
</html>
