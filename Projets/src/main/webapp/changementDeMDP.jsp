<%--
  Created by IntelliJ IDEA.
  User: adrien
  Date: 28/01/2022
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Changement de mot de passe</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <form method="post" action="ChangerMDP">
        <input type="password" name="mdpActuel" placeholder="Mot de passe actuel"/>
        <input type="password" name="newMDP" placeholder="Nouveau mot de passe"/>
        <input class="button" type="submit" value="Valider"/>
    </form>
</body>
</html>
