<%--
  Created by IntelliJ IDEA.
  User: adrien
  Date: 03/01/2022
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profil</title>
</head>
<body>
    <%@include file="link.jsp"%>
    Bienvenue <%=request.getSession().getAttribute("name")%> !!!
</body>
</html>
