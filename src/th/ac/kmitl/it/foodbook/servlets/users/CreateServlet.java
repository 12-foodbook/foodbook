package th.ac.kmitl.it.foodbook.servlets.users;

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
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.UsersDAO;

@WebServlet("/users/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateServlet() {
    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");
		
		if (!password.equals(confirmPassword)) {
			return;
		}
		
		User user = null;
    	
    	DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
		
		boolean isSuccess = false;
    	
    	try {
    		Connection conn = ds.getConnection();
    		UsersDAO usersDAO = new UsersDAO(conn);

    		user = new User();
    		
    		user.setUsername(username);
    		
    		byte[] saltBytes = PasswordManager.getSalt();
    		String salt = PasswordManager.bytesToString(saltBytes);
    		byte[] hashedPasswordBytes = PasswordManager.hashPassword(password, saltBytes);
    		String hashedPassword = PasswordManager.bytesToString(hashedPasswordBytes);
    		
    		user.setHashed_password(hashedPassword);
    		user.setSalt(salt);
    		
			isSuccess = usersDAO.create(user);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(500);
		}
		
		HttpSession session = request.getSession();

    	if (isSuccess) {
    		session.setAttribute("alert", "success");
    		response.sendRedirect("/");
    	} else {
    		session.setAttribute("alert", "fail");
    	}
    }

}
