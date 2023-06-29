<%--
  Created by IntelliJ IDEA.
  User: 13529
  Date: 2023/6/5
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="com.guo.mvc.mode.*" %>
<% User user = (User) request.getAttribute("user"); %>
<html>
<head>
    <title>Hello World - JSP</title>
</head>
<body>
  <h1>Hello <%=user.name%></h1>
    <p>
        School Name:
        <span style="color:red">
            <%=user.school.name%>
        </span>
    </p>
  <p>
      School Address:
      <span style="color:red">
            <%=user.school.address%>
        </span>
  </p>
</body>
</html>
