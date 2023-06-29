<%--
  Created by IntelliJ IDEA.
  User: 13529
  Date: 2023/6/5
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Hello World - JSP</title>
</head>
<body>
<%--    JSP Comment--%>
<h1>Hello World!</h1>
<p>
    <%
    out.println("Your IP address is ");
    %>
    <span style="color:deepskyblue">
        <%=request.getRemoteAddr()%>
    </span>
</p>
</body>
</html>
