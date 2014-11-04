package th.ac.kmitl.it.foodbook.servlets.moderators;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.Moderator;
import th.ac.kmitl.it.foodbook.daos.ModeratorsDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/moderators/authenticate")
public class AuthenticateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AuthenticateServlet() {
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.getRequestDispatcher("/WEB-INF/views/moderators/authenticate.jsp").include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
		
		Moderator moderator = null;
    	
    	DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
		
		try {
			Connection conn = ds.getConnection();
			ModeratorsDAO moderatorsDAO = new ModeratorsDAO(conn);
			
			moderator =moderatorsDAO.authenticate(username, password);
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(500);
		}
		
		HttpSession session = request.getSession();
		
		if (moderator != null) {
			session.setAttribute("moderator", moderator);
			
			session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Authenticated Successfully!"));
			response.sendRedirect("/");
		} else {
			session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Authenticated Unsuccessfully!"));
			request.getRequestDispatcher("/WEB-INF/views/moderators/authenticate.jsp").include(request, response);
		}
    }

}
