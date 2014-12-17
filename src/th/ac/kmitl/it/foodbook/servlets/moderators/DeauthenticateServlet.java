package th.ac.kmitl.it.foodbook.servlets.moderators;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/moderators/deauthenticate")
public class DeauthenticateServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public DeauthenticateServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("moderator", null);
        session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Deauthenticated successfully :D"));
        response.sendRedirect("/");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
}
