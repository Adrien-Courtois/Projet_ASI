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

public class AjouterClientServlet  extends HttpServlet {

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

        //On récupère les valeurs du formulaire via la requête POST
        String login = request.getParameter("login");
        String mdp = request.getParameter("mdp");

        //On regarde si les champs sont correctement remplies
        if(login.equals("") || mdp.equals("")){

            //Si les champs sont mal remplies alors on affiche un message d'erreur
            out.print("Veuillez remplir les champs du formulaire");
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

                //On initialise le client
                Client client = null;

                //On vérifie que la demande est faite par un conseiller ou par un client
                if(session.getAttribute("idUser") != null){

                    //On créer le nouveau client
                    client = new Client(login, mdp, (int)session.getAttribute("idUser"));
                }else{

                    //On créer le nouveau client
                    client = new Client(login, mdp);
                }

                //On l'enregistre dans la base de données
                entityManager.persist(client);

                //On applique les changements fait durant la transaction
                trans.commit();

                //On ferme l'entity manager
                entityManager.close();
                entityManagerFactory.close();

                if(session.getAttribute("idUser") != null){

                    //On redirige vers le profil du conseiller
                    request.getRequestDispatcher("profilConseiller.jsp").forward(request, response);
                }else{

                    //On redirige vers la page de login
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }finally {

                //On ferme l'entity manager
                if ( entityManager != null ) entityManager.close();
                if ( entityManagerFactory != null ) entityManagerFactory.close();
            }
        }
    }
}
