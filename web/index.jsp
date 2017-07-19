<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/17
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <script type="text/javascript">
  function submit() {
  with(document.getElementById("queryFunction")) {
  action="/customerList";
  method="GET";
  submit();
  }
  }
  </script>
  </body>
</html>
