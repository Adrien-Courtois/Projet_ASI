import Entity.Carte;
import Entity.Compte;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

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

        //On initialise l'entity manager
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {

            //On récupère l'entity manager de notre base
            entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
            entityManager = entityManagerFactory.createEntityManager();

            //On récupère les informations du compte
            Compte compte = entityManager.createQuery("select c from Compte c where c.numCompte = '" + idcompte + "'", Compte.class).getSingleResult();

            //On débute une nouvelle transaction
            EntityTransaction trans = entityManager.getTransaction();
            trans.begin();

            //On créer la carte
            Carte carte = new Carte();

            //On l'enregistre dans la bdd
            entityManager.persist(carte);

            //On lie la carte au compte
            compte.setIdCarte(carte.getIdCarte());
            entityManager.flush();

            //On applique les changements fait durant la transaction
            trans.commit();
        }finally {

            //On ferme l'entity manager
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }

        //On redirige vers la page du compte
        request.getRequestDispatcher("compte.jsp").forward(request, response);
    }
}