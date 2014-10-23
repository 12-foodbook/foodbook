package th.ac.kmitl.it.foodbook.servlets.users;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.Foodbook;
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
		
		DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
		
		boolean isSuccess = false;
    	
    	try {
			Connection conn = ds.getConnection();
			UsersDAO usersDAO = new UsersDAO(conn);
			
			User user = new User();
			
			user.setUsername(username);
			
			byte[] saltBytes = Foodbook.getSalt();
			String salt = Foodbook.bytesToString(saltBytes);
			byte[] hashedPasswordBytes = Foodbook.hashPassword(password, saltBytes);
			String hashedPassword = Foodbook.bytesToString(hashedPasswordBytes);
			
			user.setHashed_password(hashedPassword);
			user.setSalt(salt);
			
			isSuccess = usersDAO.create(user);
			
	    	conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			
			response.sendError(500);
		}
    	
    	if (isSuccess) {
    		
    	} else {
    		
    	}
    }

}
