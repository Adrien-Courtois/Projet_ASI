<%--
  Created by IntelliJ IDEA.
  User: adrien
  Date: 28/01/2022
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajouter un client</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <form method="post" action="AjouterClient">
        <input type="text" name="login" placeholder="Login du client"/>
        <input type="password" name="mdp" placeholder="Mot de passe du client"/>
        <input class="button" type="submit" value="Enregistrer"/>
    </form>
</body>
</html>
