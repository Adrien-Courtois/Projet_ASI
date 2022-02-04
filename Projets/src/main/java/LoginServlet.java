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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
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

            //On récupère le conseiller manager
            ConseillerManagerRemote conseillerManagerRemote = EjbLocator.getLocator().getConseillerManager();

            //On récupère le conseiller en fonction du login/mdp
            Conseiller conseiller = conseillerManagerRemote.getConseillerParLoginMdp(name, password);

            if(conseiller != null){

                //Si nous avons trouvé un conseiller alors on initialise la session et les variables de session
                HttpSession session = request.getSession();

                //Le nom du conseiller
                session.setAttribute("name", name);

                //On indique que c'est un conseiller et non pas un client
                session.setAttribute("client", false);

                //L'identifiant du conseiller
                session.setAttribute("idUser", conseiller.getIdConseiller());
            }else{

                out.print("Association Login/Mot de passe invalide");
            }

        }else if(submit.equals("Client")){

            //Connexion Client

            //On récupère le client manager
            ClientManagerRemote clientManagerRemote = EjbLocator.getLocator().getClientManager();

            //On récupère le client en fonction du login/mdp
            Client client = clientManagerRemote.getClientParLoginMdp(name, password);


            //On regarde si nous avons réussi à trouvé une correspondance
            if (client != null) {

                //Si nous avons réussi alors nous initialisons une session et les variables de sessions
                HttpSession session = request.getSession();

                //Le nom du client (login)
                session.setAttribute("name", name);

                //On indique que c'est un client et non pas un conseiller
                session.setAttribute("client", true);

                //On indique l'identifiant du client
                session.setAttribute("idUser", client.getIdClient());

                //On redirique vers le profil du client
                request.getRequestDispatcher("profil.jsp").forward(request, response);
            } else {

                //Si nous n'avons pas trouver de correspondance alors on affiche un message d'erreur
                out.print("Mot de passe ou nom d'utilisateur incorrect");
            }

            //On ferme la sortie
            out.close();
        }
    }
}  