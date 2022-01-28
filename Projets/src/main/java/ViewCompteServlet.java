import Entity.Compte;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ViewCompteServlet extends HttpServlet {

/*
    Servlet appelée pour voir un compte en banque en détail (voir si nous avons les autorisations)
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

        //On récupère le numéro de compte depuis la variable "compte" de la requête GET
        int numCompte = Integer.parseInt(request.getParameter("compte"));

        //On définit une variable de session "compte" pour pouvoir y accèder de partout
        session.setAttribute("compte", numCompte);

        //On vérifie que le compte appartient bien à la personne qui est connectée
        boolean autorisation = false;

        //On initialise l'entityManager
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try{
            //On récupère l'entityManager de nos entités
            entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
            entityManager = entityManagerFactory.createEntityManager();

            //On regarde si la session ouverte est la session d'un client ou d'un conseiller
            if((boolean) session.getAttribute("client") == true){

                //On regarde si le compte appartient au client de la session active
                List<Compte> comptes = entityManager.createQuery("select c from Compte c where c.numCompte = '" + numCompte + "' and c.idClient = '" + session.getAttribute("idUser") + "'", Compte.class).getResultList();
                if (comptes.size() > 0){
                    autorisation = true;
                }
            }else if((boolean) session.getAttribute("client") == false){

                //On regarde si le compte appartient à un client du conseiller de la session active
                List<Compte> comptes = entityManager.createQuery("select c from Compte c where c.numCompte = '" + numCompte + "' and c.idClient in (select cl from Client cl where cl.idConseiller = '" + session.getAttribute("idUser") + "')", Compte.class).getResultList();
                if (comptes.size() > 0){
                    autorisation = true;
                }
            }

        }finally {

            //On ferme les entityManager
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
        if(autorisation){

            //Si nous avons l'autorisation alors on affiche le compte
            request.getRequestDispatcher("compte.jsp").forward(request, response);
        }else{

            //Sinon on affiche un message d'erreur
            out.print("Vous n'êtes pas autorisé à voir ce compte");
        }

    }
}
