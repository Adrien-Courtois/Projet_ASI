import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ViewClient")
public class ViewClientServlet extends HttpServlet {

/*
    Servlet appelée pour voir un compte en banque en détail (voir si nous avons les autorisations)
*/

    //méthode appelée lors d'une requête GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Réponse de type html
        response.setContentType("text/html");

        //Récupération de la session
        HttpSession session = request.getSession();

        //Récupération de la sortie
        PrintWriter out = response.getWriter();

        if((boolean) session.getAttribute("client") == true){

            //Si on est un client alors nous n'avons pas accès à cette page
            out.print("Vous ne pouvez pas accéder à cette page");
        }else{

            //Si nous avons l'autorisation alors on affiche le compte
            request.getRequestDispatcher("compteClient.jsp?client=" + request.getParameter("client")).forward(request, response);
        }
    }
}