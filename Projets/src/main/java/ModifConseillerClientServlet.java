
import Entity.Carte;
import Entity.CarteManagerRemote;
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


@WebServlet("/ModifConseillerClient")
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

            //On récupère le client manager
            ClientManagerRemote clientManagerRemote = EjbLocator.getLocator().getClientManager();

            Client client = clientManagerRemote.getClient(idClient);

            client = clientManagerRemote.setConseiller(client, (int)session.getAttribute("idUser"));

            //On redirige vers le profil du conseiller
            request.getRequestDispatcher("profilConseiller.jsp").forward(request, response);
        }
    }
}