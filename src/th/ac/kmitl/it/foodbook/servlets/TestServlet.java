package th.ac.kmitl.it.foodbook.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.UsersDAO;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TestServlet() {
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/plain");
    	PrintWriter out = response.getWriter();
    	
    	DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
    	
		try {
			Connection conn = ds.getConnection();
	    	
	    	UsersDAO usersDAO = new UsersDAO(conn);
			
	    	User user = usersDAO.authenticate("test", "test");
	    	
	    	if (user != null) {
	    		out.println(user.getUsername());
	    		out.println(user.getHashed_password());
	    	}
	    	
	    	conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}
