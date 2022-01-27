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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        int idCompteDest = Integer.parseInt(request.getParameter("compteDest"));
        int idCompteExp = Integer.parseInt(request.getParameter("compteExp"));
        float montant = Float.parseFloat(request.getParameter("montant"));

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
            entityManager = entityManagerFactory.createEntityManager();

            Compte compte = entityManager.createQuery("select c from Compte c where c.numCompte = '" + idCompteExp + "'", Compte.class).getSingleResult();

            //On vérifie que le solde du compte est suffisant
            if(compte.getSolde() < montant){
                out.print("Vous n'avez pas assez d'argent sur votre compte");
            }else{
                //On vérifie que le compte destinataire existe
                Compte compteDest = null;
                boolean ok = false;
                try{
                    compteDest = entityManager.createQuery("select c from Compte c where c.numCompte = '" + idCompteDest + "'", Compte.class).getSingleResult();
                    ok = true;
                    if (compteDest.getPlafond() != null && compteDest.getPlafond() < compteDest.getSolde() + montant){
                        ok = false;
                        out.print("Le compte épargne de destination atteint le plafond avec le virement");
                    }
                }catch (Exception e){
                    ok = false;
                    out.print("Le compte destinataire n'existe pas");
                }
                if(ok){
                    //On commence une transaction
                    EntityTransaction trans = entityManager.getTransaction();
                    trans.begin();

                    //On créer une transaction entre le compte expéditeur et le compte destinataire
                    Transaction newTransac = new Transaction(compte.getNumCompte(), compteDest.getNumCompte(), montant);

                    //On enregistre la transaction
                    entityManager.persist(newTransac);

                    //On fais le lien dans la table de jointure
                    entityManager.persist(new CompteTransaction(newTransac.getIdTransaction(), compte.getNumCompte()));
                    entityManager.persist(new CompteTransaction(newTransac.getIdTransaction(), compteDest.getNumCompte()));

                    //On modifie le solde des comptes
                    compte.setSolde(compte.getSolde() - montant);
                    compteDest.setSolde(compteDest.getSolde() + montant);
                    entityManager.flush();

                    trans.commit();

                    entityManager.close();
                    entityManagerFactory.close();

                    request.getRequestDispatcher("compte.jsp").forward(request, response);
                }
            }

        }finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
    }
}
