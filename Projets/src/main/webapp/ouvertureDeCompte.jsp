<%--
  Created by IntelliJ IDEA.
  User: adrien
  Date: 27/01/2022
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ouverture de compte</title>
</head>
<body>
    <%@include file="link.jsp"%>
    <form action="CreationCompte" method="post">
        <input type="text" name="solde" placeholder="Solde de départ"/>
        <input id="epargne" type="checkbox" name="epargne" <%
            if(request.getParameter("epargne").equals("1")){
                %>checked value="1"<%
            }else{
                %>value="0"<%
            }
        %>/>
        <input id="plafond" type="number" name="plafond" value="0"<%
            if(request.getParameter("epargne").equals("0")){
                %>style="display: none"<%
            }
        %>/>
        <input type="submit" value="Créer le compte"/>
        <script>
            document.getElementById("epargne").addEventListener('click', () =>{
                if(document.getElementById("epargne").checked == true){
                    document.getElementById("plafond").style.display = "inline"
                    document.getElementById("epargne").value = "1"
                }else{
                    document.getElementById("plafond").style.display = "none"
                    document.getElementById("epargne").value = "0"
                    document.getElementById("plafond").value = "0"
                }
            })
        </script>
    </form>
</body>
</html>
