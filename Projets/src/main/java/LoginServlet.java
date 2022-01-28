import com.google.common.hash.Hashing;

import Entity.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class LoginServlet extends HttpServlet {

/*
    Servlet appelée pour se connecter à sa session grâce à ses login et mot de passe
*/

    //méthode appelée lors d'une requête POST
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Réponse de type html
        response.setContentType("text/html");

        //Récupération de la sortie
        PrintWriter out=response.getWriter();

        //On récupère les paramètre émit de la requête POST
        String name=request.getParameter("name");
        String password=request.getParameter("password");
        String submit=request.getParameter("sub");

        //On regarde si la requête a été émise par le bouton conseiller ou par le bouton client
        if(submit.equals("Conseiller")){

            //Connexion Conseiller
            //On initialise l'entity manager
            EntityManagerFactory entityManagerFactory = null;
            EntityManager entityManager = null;

            //On initialise l'idConseiller
            int idConseiller = 0;

            try {

                //On récupère l'entity manager de notre base
                entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
                entityManager = entityManagerFactory.createEntityManager();

                //On récupère la liste des conseillers dont le login est le même que celui indiqué dans le formulaire
                List<Conseiller> conseillers = entityManager.createQuery("select c from Conseiller c where c.loginConseiller = '" + name + "'", Conseiller.class).getResultList();

                //On boucle sur la liste afin de trouver un ensemble login/mot de passe qui correspond avec ce qui a été entré dans le formulaire
                for (Conseiller conseiller : conseillers) {

                    //On hash le mot de passe entré dans le formulaire car les mot de passe sont hashés dans la base pour une question de sécurité
                    if (conseiller.getMdpConseiller().equals(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString())) {

                        //Si on trouve une correspondance alors on attribut à la variable idConseiller l'identifiant du conseiller
                        idConseiller = conseiller.getIdConseiller();
                    }
                }

                //On regarde si nous avons réussi à trouver une correspondance login/mot de passe
                if (idConseiller != 0) {

                    //Si nous avons trouvé un conseiller alors on initialise la session et les variables de session
                    HttpSession session = request.getSession();

                    //Le nom du conseiller
                    session.setAttribute("name", name);

                    //On indique que c'est un conseiller et non pas un client
                    session.setAttribute("client", false);

                    //L'identifiant du conseiller
                    session.setAttribute("idUser", idConseiller);

                    //On ferme l'entity manager
                    entityManager.close();
                    entityManagerFactory.close();

                    //On redirige vers le profil du conseiller
                    request.getRequestDispatcher("profilConseiller.jsp").forward(request, response);
                } else {

                    //Sinon on affiche un message d'erreur
                    out.print("Mot de passe ou nom d'utilisateur incorrect");
                }

                //On ferme la sortie
                out.close();
            }finally {

                //On ferme l'entity manager
                if ( entityManager != null ) entityManager.close();
                if ( entityManagerFactory != null ) entityManagerFactory.close();
            }

        }else if(submit.equals("Client")){

            //Connexion Client
            //Si nous avons appuyer sur le bouton de connexion cliente alors on initialise l'entity manager
            EntityManagerFactory entityManagerFactory = null;
            EntityManager entityManager = null;

            //On initialise un booléen à faux qui va nous servir à voir si nous avons réussi à trouver une correspondance
            Boolean connexion = false;

            try {

                //On récupère l'entity manager de notre base
                entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
                entityManager = entityManagerFactory.createEntityManager();

                //On initialise l'idClient
                int idClient = 0;

                //On recherche tout les clients qui ont pour login le login entrée dans le formulaire
                List<Client> clients = entityManager.createQuery("select c from Client c where c.loginClient = '" + name + "'", Client.class).getResultList();

                //On boucle sur la liste de clients pour trouver une correspondance login/mot de passe
                for (Client client : clients) {

                    //On vérifie le hash du mot de passe entré avec le mot de passe enregistré dans la base
                    if (client.getMdpClient().equals(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString())) {

                        //Si nous trouvons une correspondance alors nous mettons l'identifiant du client dans la variable idClient
                        idClient = client.getIdClient();
                    }
                }

                //On regarde si nous avons réussi à trouvé une correspondance
                if (idClient != 0) {

                    //Si nous avons réussi alors nous initialisons une session et les variables de sessions
                    HttpSession session = request.getSession();

                    //Le nom du client (login)
                    session.setAttribute("name", name);

                    //On indique que c'est un client et non pas un conseiller
                    session.setAttribute("client", true);

                    //On indique l'identifiant du client
                    session.setAttribute("idUser", idClient);

                    //On ferme l'entity manager
                    entityManager.close();
                    entityManagerFactory.close();

                    //On redirique vers le profil du client
                    request.getRequestDispatcher("profil.jsp").forward(request, response);
                } else {

                    //Si nous n'avons pas trouver de correspondance alors on affiche un message d'erreur
                    out.print("Mot de passe ou nom d'utilisateur incorrect");
                }

                //On ferme la sortie
                out.close();
            }finally {

                //On ferme l'entity manager
                if ( entityManager != null ) entityManager.close();
                if ( entityManagerFactory != null ) entityManagerFactory.close();
            }
        }
    }
}  