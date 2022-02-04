import Entity.Compte;
import Entity.CompteManagerRemote;
import Entity.Conseiller;
import Entity.ConseillerManagerRemote;

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

@WebServlet("/CreationCompte")
public class CreationCompteServlet extends HttpServlet {

/*
    Servlet appelée pour créer un compte
*/

    //méthode appelée lors d'une requête POST
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Réponse de type html
        response.setContentType("text/html");

        //Récupération de la session
        HttpSession session = request.getSession();

        //Récupération de la sortie
        PrintWriter out=response.getWriter();

        //On récupère le solde à partir du formulaire
        int solde = 0;
        if(request.getParameter("solde") != null && request.getParameter("solde") != ""){
            solde = Integer.parseInt(request.getParameter("solde"));
        }

        //On récupère la valeur de la checkbox du formulaire
        int epargne = 0;
        if(request.getParameter("epargne") != null && request.getParameter("epargne") != ""){
            epargne = Integer.parseInt(request.getParameter("epargne"));
        }

        //On récupère le plafond du formulaire
        float plafond = 0;
        if(request.getParameter("plafond") != null && request.getParameter("plafond") != ""){
            plafond = Float.parseFloat(request.getParameter("plafond"));
        }

        //On récupère le conseiller manager
        CompteManagerRemote compteManagerRemote = EjbLocator.getLocator().getCompteManager();

        //On modifie le mot de passe du conseiller
        Compte compte = compteManagerRemote.ajouterCompte(epargne, (float)solde, plafond, (int)session.getAttribute("idUser"));

        if(compte != null){

            //On redirige vers le profil du client
            request.getRequestDispatcher("profil.jsp").forward(request, response);
        }else {

            out.print("Erreur dans les entrées du formulaire");
        }
    }
}