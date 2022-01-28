import Entity.Client;
import Entity.Conseiller;
import com.google.common.hash.Hashing;

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
import java.nio.charset.StandardCharsets;

public class ChangerMDPServlet extends HttpServlet {

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

        //On récupère les valeur du formulaire émit par requête POST
        String mdp = request.getParameter("mdpActuel");
        String newMdp = request.getParameter("newMDP");

        //On regarde si le nouveau mot de passe est vide
        if(newMdp.equals("")){
            out.print("Nouveau mot de passe vide");
        }

        //On initialise l'entity manager
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {

            //On récupère l'entity manager de notre base
            entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
            entityManager = entityManagerFactory.createEntityManager();

            //On regarde si l'utilisateur est un client ou un conseiller
            if((boolean) session.getAttribute("client")){

                //On récupère les informations du client
                Client client = entityManager.createQuery("select c from Client c where c.idClient = '" + session.getAttribute("idUser") + "'", Client.class).getSingleResult();

                if(client.getMdpClient().equals(Hashing.sha256().hashString(mdp, StandardCharsets.UTF_8).toString())){

                    //On commence une transaction
                    EntityTransaction trans = entityManager.getTransaction();
                    trans.begin();

                    //on remplace le mot de passe du client par le hashé du nouveau mot de passe entré
                    client.setMdpClient(Hashing.sha256().hashString(newMdp, StandardCharsets.UTF_8).toString());

                    //On applique le changement effectué sur le client
                    entityManager.flush();

                    //On applique les changements fait durant la transaction
                    trans.commit();

                    //On ferme l'entity manager
                    entityManager.close();
                    entityManagerFactory.close();

                    //On redirige vers le profil du client
                    request.getRequestDispatcher("profil.jsp").forward(request, response);
                }else{

                    //Sinon on affiche un message d'erreur
                    out.print("Le mot de passe entré ne correspond pas au mot de passe du compte");
                }
            }else{

                //On récupère les informations du client
                Conseiller conseiller = entityManager.createQuery("select c from Conseiller c where c.idConseiller = '" + session.getAttribute("idUser") + "'", Conseiller.class).getSingleResult();

                if(conseiller.getMdpConseiller().equals(Hashing.sha256().hashString(mdp, StandardCharsets.UTF_8).toString())){

                    //On commence une transaction
                    EntityTransaction trans = entityManager.getTransaction();
                    trans.begin();

                    //on remplace le mot de passe du client par le hashé du nouveau mot de passe entré
                    conseiller.setMdpConseiller(Hashing.sha256().hashString(newMdp, StandardCharsets.UTF_8).toString());

                    //On applique le changement effectué sur le client
                    entityManager.flush();

                    //On applique les changements fait durant la transaction
                    trans.commit();

                    //On ferme l'entity manager
                    entityManager.close();
                    entityManagerFactory.close();

                    //On redirige vers le profil du client
                    request.getRequestDispatcher("profilConseiller.jsp").forward(request, response);
                }else{

                    //Sinon on affiche un message d'erreur
                    out.print("Le mot de passe entré ne correspond pas au mot de passe du compte");
                }
            }
        }finally {

            //On ferme l'entity manager
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
    }
}
