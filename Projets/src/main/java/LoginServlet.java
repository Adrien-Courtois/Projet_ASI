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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        String name=request.getParameter("name");
        String password=request.getParameter("password");
        String submit=request.getParameter("sub");

        if(submit.equals("Conseiller")){

            //Connexion Conseiller
            EntityManagerFactory entityManagerFactory = null;
            EntityManager entityManager = null;

            int idConseiller = 0;

            try {
                entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
                entityManager = entityManagerFactory.createEntityManager();

                List<Conseiller> conseillers = entityManager.createQuery("select c from Conseiller c where c.loginConseiller = '" + name + "'", Conseiller.class).getResultList();
                for (Conseiller conseiller : conseillers) {
                    if (conseiller.getMdpConseiller().equals(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString())) {
                        idConseiller = conseiller.getIdConseiller();
                    }
                }

                if (idConseiller != 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("name", name);
                    session.setAttribute("client", false);
                    session.setAttribute("idUser", idConseiller);

                    entityManager.close();
                    entityManagerFactory.close();

                    request.getRequestDispatcher("profilConseiller.jsp").forward(request, response);
                } else {
                    out.print("Mot de passe ou nom d'utilisateur incorrect");
                }
                out.close();
            }finally {
                if ( entityManager != null ) entityManager.close();
                if ( entityManagerFactory != null ) entityManagerFactory.close();
            }

        }else if(submit.equals("Client")){

            //Connexion Client

            EntityManagerFactory entityManagerFactory = null;
            EntityManager entityManager = null;

            Boolean connexion = false;

            try {
                entityManagerFactory = Persistence.createEntityManagerFactory("Clients");
                entityManager = entityManagerFactory.createEntityManager();

                int idClient = 0;

                List<Client> clients = entityManager.createQuery("select c from Client c where c.loginClient = '" + name + "'", Client.class).getResultList();
                for (Client client : clients) {
                    if (client.getMdpClient().equals(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString())) {
                        idClient = client.getIdClient();
                    }
                }

                if (idClient != 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("name", name);
                    session.setAttribute("client", true);
                    session.setAttribute("idUser", idClient);

                    entityManager.close();
                    entityManagerFactory.close();

                    request.getRequestDispatcher("profil.jsp").forward(request, response);
                } else {
                    out.print("Mot de passe ou nom d'utilisateur incorrect");
                }
                out.close();
            }finally {
                if ( entityManager != null ) entityManager.close();
                if ( entityManagerFactory != null ) entityManagerFactory.close();
            }
        }
    }
}  