<%@ page import="javax.persistence.Persistence" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.common.hash.Hashing" %>
<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.EntityManager" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="Entity.Client" %><%--
  Created by IntelliJ IDEA.
  User: adrien
  Date: 26/01/2022
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profil Conseiller</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <%@include file="link.jsp"%>
    <h2>Bonjour <%=request.getSession().getAttribute("name")%></h2>
    <p>Voici vos clients</p>
    <a href="changementDeMDP.jsp"><button>Changer de mot de passe</button></a>
    <a href="ajouterClient.jsp"><button>Ajouter un client</button></a>

    <table>
        <thead>
            <tr>
                <th>Nom du client</th>
            </tr>
        </thead>
        <tbody>

<%
    EntityManagerFactory entityManagerFactory = null;
    EntityManager entityManager = null;

    try {
        entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
        entityManager = entityManagerFactory.createEntityManager();

        List<Client> clients = entityManager.createQuery("select c from Client c where c.idConseiller = '" + request.getSession().getAttribute("idUser") + "'", Client.class).getResultList();
        for (Client client : clients) {%>
            <tr>
                <td><a href="ViewClient?client=<%=client.getIdClient()%>"><%=client.getLoginClient()%></a></td>
            </tr>
        <%}
%>

        </tbody>
    </table>

<p>Ajouter un client à votre charge :</p>
    <form action="ModifConseillerClient" method="post">
<select name="clients">
    <option value="0">Sélectionner un client à ajouter</option>
    <%
        clients = entityManager.createQuery("select c from Client c where c.idClient not in (select cl from Client cl where cl.idConseiller = '" + request.getSession().getAttribute("idUser") + "')", Client.class).getResultList();
            for (Client client : clients) {
                %>
                    <option value="<%=client.getIdClient()%>"><%=client.getLoginClient()%></option>
                <%
            }

        }finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
    %>
</select>
<input class="button" type="submit" value="ajouter"/>
</form>
</body>
</html>
