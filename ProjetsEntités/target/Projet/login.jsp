<%@include file="link.jsp"%>

<form action="Login" method="post">
    Nom <input type="text" name="name"><br>
    MDP <input type="password" name="password"><br>
    <input class="button" type="submit" name="sub" value="Client">
    <input class="button" type="submit" name="sub" value="Conseiller">
</form>
<a href="ajouterClient.jsp"><button>Inscription</button></a>