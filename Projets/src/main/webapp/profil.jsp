<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.EntityManager" %>
<%@ page import="javax.persistence.Persistence" %>
<%@ page import="Entity.Client" %>
<%@ page import="java.util.List" %>
<%@ page import="Entity.Compte" %><%--
  Created by IntelliJ IDEA.
  User: adrien
  Date: 03/01/2022
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profil Client</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <%@include file="link.jsp"%>
    Bienvenue <%=request.getSession().getAttribute("name")%>

    <a href="changementDeMDP.jsp"><button>Changer de mot de passe</button></a>

    <p>Voici vos comptes courants</p><a href="ouvertureDeCompte.jsp?epargne=0"><button>Ouvrir un compte courant</button></a>

    <table>
        <thead>
        <tr>
            <th>Numéro de compte</th>
            <th>Solde</th>
        </tr>
        </thead>
        <tbody>

        <%
            EntityManagerFactory entityManagerFactory = null;
            EntityManager entityManager = null;

            try {
                entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
                entityManager = entityManagerFactory.createEntityManager();

                List<Compte> comptes = entityManager.createQuery("select c from Compte c where c.idClient = '" + request.getSession().getAttribute("idUser") + "' and c.plafond = NULL", Compte.class).getResultList();
                for (Compte compte : comptes) {%>
        <tr>
            <td><a href="ViewCompte?compte=<%=compte.getNumCompte()%>"><%=compte.getNumCompte()%></a></td>
            <td><%=compte.getSolde()%></td>
        </tr>
        <%}
        }finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
        %>

        </tbody>
    </table>

    <p>Voici vos comptes épargnes</p><a href="ouvertureDeCompte.jsp?epargne=1"><button>Ouvrir un compte épargne</button></a>

    <table>
        <thead>
        <tr>
            <th>Numéro de compte</th>
            <th>Solde</th>
            <th>Plafond</th>
        </tr>
        </thead>
        <tbody>

        <%
            entityManagerFactory = null;
            entityManager = null;

            try {
                entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
                entityManager = entityManagerFactory.createEntityManager();

                List<Compte> comptes = entityManager.createQuery("select c from Compte c where c.idClient = '" + request.getSession().getAttribute("idUser") + "' and c.plafond != NULL", Compte.class).getResultList();
                for (Compte compte : comptes) {%>
        <tr>
            <td><a href="ViewCompte?compte=<%=compte.getNumCompte()%>"><%=compte.getNumCompte()%></a></td>
            <td><%=compte.getSolde()%></td>
            <td><%=compte.getPlafond()%></td>
        </tr>
        <%}
        }finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
        %>

        </tbody>
    </table>
</body>
</html>
