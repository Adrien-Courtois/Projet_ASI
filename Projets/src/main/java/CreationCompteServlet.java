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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        int solde = Integer.parseInt(request.getParameter("solde"));
        int epargne = 0;
        if(request.getParameter("epargne") != null && request.getParameter("epargne") != ""){
            epargne = Integer.parseInt(request.getParameter("epargne"));
        }

        float plafond = 0;
        System.out.println(request.getParameter("plafond"));
        if(request.getParameter("plafond") != null && request.getParameter("plafond") != ""){
            plafond = Float.parseFloat(request.getParameter("plafond"));
        }

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
            entityManager = entityManagerFactory.createEntityManager();

            //On commence une transaction
            EntityTransaction trans = entityManager.getTransaction();
            trans.begin();
            Compte compte = null;
            if (epargne == 1){
                if(plafond > 0 && solde < plafond) {
                    compte = new Compte(solde, (int) session.getAttribute("idUser"), plafond);
                }else{
                    out.print("Montant du plafond invalide");
                }
            }else{
                compte = new Compte(solde, (int)session.getAttribute("idUser"), null);
            }

            if(compte != null)
                entityManager.persist(compte);
            trans.commit();

            entityManager.close();
            entityManagerFactory.close();
            request.getRequestDispatcher("profil.jsp").forward(request, response);

        }finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
    }
}