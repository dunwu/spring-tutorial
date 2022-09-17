<#--<#import "/spring.ftl" as spring />-->
<!DOCTYPE html>
<html>
<head>
  <title>${title}</title>
  <#assign home><@spring.url relativeUrl="/"/></#assign>
  <link rel="stylesheet" href="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/css/bootstrap.min.css" />
</head>
<body>
<div class="container">
  <div class="row clearfix">
    <div class="col-md-12 column">
      <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse"
                  data-target="#bs-example-navbar-collapse-1"><span class="sr-only">Toggle navigation</span><span
                class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="http://spring.io/"> Spring </a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            <li class="active">
              <a class="brand" href="http://getbootstrap.com/"> Bootstrap </a>
            </li>
            <li>
              <a class="brand" href="http://freemarker.org/"> FreeMarker </a>
            </li>
          </ul>
          <form class="navbar-form navbar-left" role="search">
            <div class="form-group">
              <input type="text" class="form-control" />
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
          </form>
          <ul class="nav navbar-nav navbar-right">
            <li>
              <a href="https://github.com/dunwu/">Github</a>
            </li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown<strong
                    class="caret"></strong></a>
              <ul class="dropdown-menu">
                <li>
                  <a href="#">Action</a>
                </li>
                <li>
                  <a href="#">Another action</a>
                </li>
                <li>
                  <a href="#">Something else here</a>
                </li>
                <li class="divider">
                </li>
                <li>
                  <a href="#">Separated link</a>
                </li>
              </ul>
            </li>
          </ul>
        </div>

      </nav>
      <div class="jumbotron">
        <h1>${title}</h1>
        <div>${message}</div>
        <div id="created">${date?datetime}</div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
