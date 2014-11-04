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

import th.ac.kmitl.it.foodbook.PasswordManager;
import th.ac.kmitl.it.foodbook.beans.Moderator;
import th.ac.kmitl.it.foodbook.daos.ModeratorsDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/moderators/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateServlet() {
    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/moderators/create.jsp").include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");
		
		if (!password.equals(confirmPassword)) {
			return;
		}
		
		Moderator moderator = null;
    	
    	DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
		
		boolean isSuccess = false;
    	
    	try {
    		Connection conn = ds.getConnection();
    		ModeratorsDAO moderatorsDAO = new ModeratorsDAO(conn);

    		moderator = new Moderator();
    		
    		moderator.setUsername(username);
    		
    		byte[] saltBytes = PasswordManager.getSalt();
    		String salt = PasswordManager.bytesToString(saltBytes);
    		byte[] hashedPasswordBytes = PasswordManager.hashPassword(password, saltBytes);
    		String hashedPassword = PasswordManager.bytesToString(hashedPasswordBytes);
    		
    		moderator.setHashed_password(hashedPassword);
    		moderator.setSalt(salt);
    		
			isSuccess = moderatorsDAO.create(moderator);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(500);
		}
		
		HttpSession session = request.getSession();

    	if (isSuccess) {
    		session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Created Successfully!"));
    		response.sendRedirect("/");
    	} else {
    		session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Created Unsuccessfully!"));
    		request.getRequestDispatcher("/WEB-INF/views/moderators/create.jsp").include(request, response);
    	}
    }

}
