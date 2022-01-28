import Entity.Compte;
import Entity.CompteTransaction;
import Entity.Transaction;

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

        //On initialise l'entityManager
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
            //On récupère l'entityManager de nos entités
            entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
            entityManager = entityManagerFactory.createEntityManager();

            //On fait une requête pour récupérer les informations du compte de l'expéditeur
            Compte compte = entityManager.createQuery("select c from Compte c where c.numCompte = '" + idCompteExp + "'", Compte.class).getSingleResult();

            //On vérifie que le solde du compte est suffisant
            if(compte.getSolde() < montant){

                //Si il n'est pas suffisant on affiche un message d'erreur
                out.print("Vous n'avez pas assez d'argent sur votre compte");
            }else{

                //Sinon on vérifie que le compte destinataire existe
                Compte compteDest = null;

                //variable utilisée pour voir si le compte existe ou non
                boolean ok = false;

                try{

                    //On essaye de récupérer les informations du compte de destination indiqué
                    compteDest = entityManager.createQuery("select c from Compte c where c.numCompte = '" + idCompteDest + "'", Compte.class).getSingleResult();

                    //Si on arrive à récupérer le compte alors on peut mettre le booléen à vrai
                    ok = true;

                    //Si c'est un compte épargne alors on regarde que le solde + le montant de la transaction n'est pas supérieur au plafond
                    if (compteDest.getPlafond() != null && compteDest.getPlafond() < compteDest.getSolde() + montant){

                        //Si c'est supérieur au plafond alors on affiche un message d'erreur
                        ok = false;
                        out.print("Le compte épargne de destination atteint le plafond avec le virement");
                    }
                }catch (Exception e){

                    //Si une erreur apparaît lors de la récupération du compte cela signifie que le compte n'existe pas
                    out.print("Le compte destinataire n'existe pas");
                }
                if(ok){
                    //Si c'est bon alors on commence une transaction (transaction de base de données)
                    EntityTransaction trans = entityManager.getTransaction();
                    trans.begin();

                    //On créer une transaction (un virement entre compte) entre le compte expéditeur et le compte destinataire
                    Transaction newTransac = new Transaction(compte.getNumCompte(), compteDest.getNumCompte(), montant);

                    //On enregistre la transaction (un virement entre compte)
                    entityManager.persist(newTransac);

                    //On fais le lien dans la table de jointure
                    entityManager.persist(new CompteTransaction(newTransac.getIdTransaction(), compte.getNumCompte()));
                    entityManager.persist(new CompteTransaction(newTransac.getIdTransaction(), compteDest.getNumCompte()));

                    //On modifie le solde des comptes
                    compte.setSolde(compte.getSolde() - montant);
                    compteDest.setSolde(compteDest.getSolde() + montant);
                    entityManager.flush();

                    //On applique les changements fait durant la transaction (transaction de base de données)
                    trans.commit();

                    //On ferme l'entity manager
                    entityManager.close();
                    entityManagerFactory.close();

                    //On affiche la page du compte
                    request.getRequestDispatcher("compte.jsp").forward(request, response);
                }
            }
        }finally {
            //On ferme l'entity manager
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
    }
}
