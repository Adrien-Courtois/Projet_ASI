import Entity.Client;
import Entity.ClientManagerRemote;

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

@WebServlet("/AjouterClient")
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
            Client client = null;

            if(session.getAttribute("idUser") != null){

                //On créer le nouveau client
                client = new Client(login, mdp, (int)session.getAttribute("idUser"));
            }else{

                //On créer le nouveau client
                client = new Client(login, mdp);
            }

            //Sinon on créer le client
            //On récupère le client manager
            ClientManagerRemote clientManagerRemote = EjbLocator.getLocator().getClientManager();

            //On ajoute le client
            clientManagerRemote.ajouterClient(client);


            if(session.getAttribute("idUser") != null){

                //On redirige vers le profil du conseiller
                request.getRequestDispatcher("profilConseiller.jsp").forward(request, response);
            }else{

                //On redirige vers la page de login
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
    }
}
