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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        PrintWriter out=response.getWriter();

        int idcompte = (int)session.getAttribute("compte");

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
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

            trans.commit();
        }finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }

        request.getRequestDispatcher("compte.jsp").forward(request, response);
    }
}