import Entity.Client;
import Entity.ClientManagerRemote;
import Entity.Conseiller;
import Entity.ConseillerManagerRemote;
import com.google.common.hash.Hashing;

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
import java.nio.charset.StandardCharsets;

@WebServlet("/ChangerMDP")
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

        //On regarde si l'utilisateur est un client ou un conseiller
        if((boolean) session.getAttribute("client")){

            //On récupère le client manager
            ClientManagerRemote clientManagerRemote = EjbLocator.getLocator().getClientManager();

            //On modifie le mot de passe du client
            Client client = clientManagerRemote.modifMDP((int)session.getAttribute("idUser"), mdp, newMdp);

            if(client != null){

                //On redirige vers le profil du client
                request.getRequestDispatcher("profil.jsp").forward(request, response);
            }else{

                //Sinon on affiche un message d'erreur
                out.print("Le mot de passe entré ne correspond pas au mot de passe du compte");
            }
        }else{

            //On récupère le conseiller manager
            ConseillerManagerRemote conseillerManagerRemote = EjbLocator.getLocator().getConseillerManager();

            //On modifie le mot de passe du conseiller
            Conseiller conseiller = conseillerManagerRemote.modifMDP((int)session.getAttribute("idUser"), mdp, newMdp);

            if(conseiller != null){
                //On redirige vers le profil du conseiller
                request.getRequestDispatcher("profilConseiller.jsp").forward(request, response);
            }else{

                //Sinon on affiche un message d'erreur
                out.print("Le mot de passe entré ne correspond pas au mot de passe du compte");
            }
        }
    }
}
