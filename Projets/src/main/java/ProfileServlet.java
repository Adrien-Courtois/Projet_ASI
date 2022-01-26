import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        HttpSession session=request.getSession(false);
        if(session!=null){

            String name = (String)session.getAttribute("name");
            Boolean client = (Boolean) request.getSession().getAttribute("client");

            //On vérifie que la session est bien en place
            if(name != null) {

                //On regarde si c'est un client ou un conseiller
                if(client){
                    request.getRequestDispatcher("profil.jsp").forward(request, response);
                }else{
                    request.getRequestDispatcher("profilConseiller.jsp").forward(request, response);
                }

            }
            else{
                out.print("Connectez-vous d'abord<br>");
                request.getRequestDispatcher("login.jsp").include(request, response);
            }
        }
        else{
            out.print("Please login first<br>");
            request.getRequestDispatcher("login.jsp").include(request, response);
        }
        out.close();
    }
} 