<%--
  Created by IntelliJ IDEA.
  User: adrien
  Date: 03/01/2022
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<a href="/Projet">Accueil</a> |

<%if(request.getSession().getAttribute("name") == null){ %>
    <a href="login.jsp">Connexion</a> |
<% }else{ %>
    <a href="LogoutServlet">Deconnexion</a> |
<% } %>

<a href="ProfileServlet">Mon profil</a>
<hr>