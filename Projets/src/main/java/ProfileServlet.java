import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfileServlet extends HttpServlet {

/*
    Servlet appelée pour rediriger vers le profil (client ou conseiller en fonction de la session ouverte)
*/

    //Méthode appelée lors d'une requête GET sur la page
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Réponse de type html
        response.setContentType("text/html");

        //Récupération de la session
        HttpSession session=request.getSession(false);

        //Récupération de la sortie
        PrintWriter out=response.getWriter();

        //On regarde si une session est en cours
        if(session!=null){

            //Si une session est ouverte alors on récupère le nom
            String name = (String)session.getAttribute("name");

            //On regarde si c'est un client ou un conseiller
            Boolean client = (Boolean) request.getSession().getAttribute("client");

            //On vérifie que la session est bien en place
            if(name != null) {

                //Si c'est un client alors on redirige vers la page de profil d'un client
                if(client){
                    request.getRequestDispatcher("profil.jsp").forward(request, response);

                //Si c'est un conseiller alors on redirige vers la page de profil d'un conseiller
                }else{
                    request.getRequestDispatcher("profilConseiller.jsp").forward(request, response);
                }
            }
            else{

                //Si la session n'est pas ouverte alors on affiche un message d'erreur et on redirige vers la page de login
                out.print("Connectez-vous d'abord<br>");
                request.getRequestDispatcher("login.jsp").include(request, response);
            }
        }
        else{

            //Si la session n'est pas ouverte alors on affiche un message d'erreur et on redirige vers la page de login
            out.print("Connectez-vous d'abord<br>");
            request.getRequestDispatcher("login.jsp").include(request, response);
        }

        //On ferme la sortie
        out.close();
    }
} 