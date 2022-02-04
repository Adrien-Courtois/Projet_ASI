import Entity.Carte;
import Entity.CarteManagerRemote;
import Entity.Compte;
import Entity.CompteManagerRemote;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/DemandeCarte")
public class DemandeCarteServlet extends HttpServlet {

/*
    Servlet appelée pour créer une carte et la lié au compte
*/

    //méthode appelée lors d'une requête GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Réponse de type html
        response.setContentType("text/html");

        //Récupération de la session
        HttpSession session = request.getSession();

        //Récupération de la sortie
        PrintWriter out=response.getWriter();

        //On récupère l'identifiant du compte depuis la variable de session
        int idcompte = (int)session.getAttribute("compte");

        //On récupère le conseiller manager
        CompteManagerRemote compteManagerRemote = EjbLocator.getLocator().getCompteManager();

        //On récupère les informations du compte
        Compte compte = compteManagerRemote.getCompte(idcompte);

        //On récupère le carte manager
        CarteManagerRemote carteManagerRemote = EjbLocator.getLocator().getCarteManager();

        Carte carte = carteManagerRemote.ajouterCarte();

        compte = compteManagerRemote.setCarte(carte, compte);

        //On redirige vers la page du compte
        request.getRequestDispatcher("compte.jsp").forward(request, response);
    }
}