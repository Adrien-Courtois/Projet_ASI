
import Entity.Client;

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

public class ModifConseillerClientServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();

        //On récupère l'identifiant du client du formulaire
        int idClient = Integer.parseInt(request.getParameter("clients"));

        if(idClient == 0){

            //Si l'identifiant est égal à 0 alors aucun client n'a été sélectionné donc on affiche un message d'erreur
            out.print("Veuillez sélectionner un client avant d'appuyer sur le bouton");
        }else{

            //On initialise l'entity manager
            EntityManagerFactory entityManagerFactory = null;
            EntityManager entityManager = null;
            try {

                //On récupère l'entity manager de notre base
                entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
                entityManager = entityManagerFactory.createEntityManager();

                //On commence une transaction
                EntityTransaction trans = entityManager.getTransaction();
                trans.begin();

                //On récupère le client qu'on a sélectionné dans le formulaire
                Client client = entityManager.createQuery("select c from Client c where c.idClient = '" + idClient + "'", Client.class).getSingleResult();

                //On lui modifie le conseiller qui lui est associé
                client.setIdConseiller((int)session.getAttribute("idUser"));

                //On applique les changements fais aux entités
                entityManager.flush();

                //On applique les changements fais durant la transaction
                trans.commit();

                //On ferme l'entity manager
                entityManager.close();
                entityManagerFactory.close();

                //On redirige vers le profil du conseiller
                request.getRequestDispatcher("profilConseiller.jsp").forward(request, response);
            }finally {

                //On ferme l'entity manager
                if ( entityManager != null ) entityManager.close();
                if ( entityManagerFactory != null ) entityManagerFactory.close();
            }
        }
    }
}