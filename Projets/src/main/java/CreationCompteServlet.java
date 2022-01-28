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

public class CreationCompteServlet extends HttpServlet {

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
        PrintWriter out=response.getWriter();

        //On récupère le solde à partir du formulaire
        int solde = 0;
        if(request.getParameter("solde") != null && request.getParameter("solde") != ""){
            solde = Integer.parseInt(request.getParameter("solde"));
        }

        //On récupère la valeur de la checkbox du formulaire
        int epargne = 0;
        if(request.getParameter("epargne") != null && request.getParameter("epargne") != ""){
            epargne = Integer.parseInt(request.getParameter("epargne"));
        }

        //On récupère le plafond du formulaire
        float plafond = 0;
        if(request.getParameter("plafond") != null && request.getParameter("plafond") != ""){
            plafond = Float.parseFloat(request.getParameter("plafond"));
        }

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

            //On initialise la variable compte
            Compte compte = null;

            //On regarde si le client veut créer un compte épargne ou non
            if (epargne == 1){

                //Si c'est un compte épargne alors on vérifie que le plafond n'est pas inférieur au solde et qu'il soit supérieur à 0
                if(plafond > 0 && solde < plafond) {

                    //Si tout est bon alors nous créons le compte
                    compte = new Compte(solde, (int) session.getAttribute("idUser"), plafond);
                }else{

                    //Sinon on affiche un message d'erreur
                    out.print("Montant du plafond invalide");
                }
            }else{

                //Si ce n'est pas un compte épargne alors on peut directement créer le comtpe en banque sans plafond
                compte = new Compte(solde, (int)session.getAttribute("idUser"), null);
            }

            //On vérifie que le compte a était créer avant de l'enregistrer
            if(compte != null) {

                //On enregistre le compte dans la base de donnée
                entityManager.persist(compte);
            }

            //On applique la transaction
            trans.commit();

            //On ferme l'entity manager
            entityManager.close();
            entityManagerFactory.close();

            //On redirige vers le profil du client
            request.getRequestDispatcher("profil.jsp").forward(request, response);

        }finally {

            //On ferme l'entity manager
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
    }
}