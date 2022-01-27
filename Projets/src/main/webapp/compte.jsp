<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.EntityManager" %>
<%@ page import="javax.persistence.Persistence" %>
<%@ page import="Entity.Compte" %>
<%@ page import="Entity.Transaction" %>
<%@ page import="java.util.List" %>
<%@ page import="Entity.Carte" %>
<%@ page import="javax.persistence.NoResultException" %><%--
  Created by IntelliJ IDEA.
  User: adrien
  Date: 26/01/2022
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <title>Compte N°<%=request.getSession().getAttribute("compte")%></title>
</head>
<body>
    <%@include file="link.jsp"%>

    <h2>Compte N°<%=request.getSession().getAttribute("compte")%></h2>

    <%
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
            entityManager = entityManagerFactory.createEntityManager();

            Compte compte = entityManager.createQuery("select c from Compte c where c.numCompte = '" + request.getSession().getAttribute("compte") + "'", Compte.class).getSingleResult();
    %>
        <p>Solde du compte : <%=compte.getSolde()%> euros</p>
    <%
        if(compte.getPlafond() == null){
            try{
                Carte carte = entityManager.createQuery("select c from Carte c where c.idCarte = '" + compte.getIdCarte() + "'", Carte.class).getSingleResult();
    %>
    <p>Votre carte : <%=carte.getIdCarte()%></p>
    <p>Votre code de carte (survoler pour découvrir): <span id="cacheCode"><%=carte.getCodeCarte()%></span></p>
    <%
            }catch (NoResultException e){
    %>
    <p>Vous n'avez pas de carte, si vous en souhaitez une, faites la demande en cliquant <a href="DemandeCarte">ici</a></p>
    <%
            }
        }
    %>
        <p>Mouvement sur le compte</p>
        <table>
            <thead>
                <tr>
                    <th style="border: 1px solid #333">Montant transaction</th>
                    <th style="border: 1px solid #333">date</th>
                </tr>
            </thead>
            <tbody>
        <%
            List<Transaction> transactions = entityManager.createQuery("select t from Transaction t where t.numCompteDest = '" + compte.getNumCompte() + "' OR t.numCompteExp = '" + compte.getNumCompte() + "' order by t.date desc", Transaction.class).getResultList();
            for (Transaction transaction: transactions) {
                if(transaction.getNumCompteExp() == compte.getNumCompte()){
                    %>
                    <tr>
                        <td style="border: 1px solid #333; background: red; color: #fff"><%=transaction.getMontant()%></td>
                        <td style="border: 1px solid #333; background: red; color: #fff"><%=transaction.getDate()%></td>
                    </tr>
                    <%
                }else{
                    %>
                    <tr>
                        <td style="border: 1px solid #333; background: green; color: #fff"><%=transaction.getMontant()%></td>
                        <td style="border: 1px solid #333; background: green; color: #fff"><%=transaction.getDate()%></td>
                    </tr>
                    <%
                }

            }
        %>
            </tbody>
        </table>
    <%
        if((boolean) session.getAttribute("client") == true){
    %>

    <p>Vous voulez effectuer un virement ?</p>
        <form action="Transaction" method="post">
            <input type="text" name="compteDest" placeholder="Numéro de compte du destinataire"/>
            <input type="hidden" name="compteExp" value="<%=compte.getNumCompte()%>"/>
            <input type="number" name="montant" placeholder="Montant"/>
            <input type="submit" value="Effectuer le virement"/>
        </form>

    <%
        }
    %>
    <%
        }finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
    %>
</body>
</html>
