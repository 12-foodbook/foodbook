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

import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.UsersDAO;

@WebServlet("/users/authenticate")
public class AuthenticateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AuthenticateServlet() {
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
		
		User user = null;
    	
    	DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
		
		try {
			Connection conn = ds.getConnection();
			UsersDAO usersDAO = new UsersDAO(conn);
			
			user = usersDAO.authenticate(username, password);
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			
			response.sendError(500);
		}
		
		HttpSession session = request.getSession();
		
		if (user != null) {
			session.setAttribute("user", user);
			session.setAttribute("alert", "เข้าสู่ระบบเรียบร้อยแล้ว");
			
			response.sendRedirect("/");
		} else {
			session.setAttribute("alert", "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
			
			request.getRequestDispatcher("/WEB-INF/views/users/authenticate.jsp").include(request, response);
		}
    }

}
