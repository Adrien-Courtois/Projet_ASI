import Entity.*;

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

@WebServlet("/Transaction")
public class TransactionServlet extends HttpServlet {

/*
    Servlet appelée pour effectuer une transaction entre deux comtpes
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

        //On récupère le numéro de compte du destinataire depuis la variable "compteDest" de la requête POST (le champ input name="compteDest")
        int idCompteDest = Integer.parseInt(request.getParameter("compteDest"));

        //On récupère le numéro de compte de l'expéditeur depuis la variable "compteExp" de la requête POST (le champ input name="compteExp")
        int idCompteExp = Integer.parseInt(request.getParameter("compteExp"));

        //On récupère le montant depuis la variable "montant" de la requête POST (le champ input name="montant")
        float montant = Float.parseFloat(request.getParameter("montant"));

        //On récupère le compte manager
        CompteManagerRemote compteManagerRemote = EjbLocator.getLocator().getCompteManager();

        //On récupère le compte
        Compte compteDest = compteManagerRemote.getCompte(idCompteDest);
        Compte compteExp = compteManagerRemote.getCompte(idCompteExp);

        if(compteExp != null){

            //On récupère le compte manager
            TransactionManagerRemote transactionManagerRemote = EjbLocator.getLocator().getTransactionManager();
            Transaction transaction = transactionManagerRemote.ajouterTransaction(compteExp, compteDest, montant);
        }else{
            out.print("Le compte destinataire n'existe pas");
        }

        TransactionManagerRemote transactionManager = EjbLocator.getLocator().getTransactionManager();

        Transaction transac = transactionManager.ajouterTransaction(compteExp, compteDest, montant);

        if(transac != null){
            //On affiche la page du compte
            request.getRequestDispatcher("compte.jsp").forward(request, response);
        }else {
            out.print("La transaction n'a pa pu s'effectué");
        }
    }
}
