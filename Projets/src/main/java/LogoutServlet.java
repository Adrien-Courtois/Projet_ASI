import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {

/*
    Servlet appelée pour se déconnecter de sa session
*/
    //méthode appelée lors d'une requête GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Réponse de type html
        response.setContentType("text/html");

        //Récupération de la session
        HttpSession session = request.getSession();

        //On ferme la session
        session.invalidate();

        //On redirige vers la page d'accueil
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}  