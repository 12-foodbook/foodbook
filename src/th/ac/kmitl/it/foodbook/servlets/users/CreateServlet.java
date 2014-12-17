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
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;
import th.ac.kmitl.it.foodbook.utils.Util;

@WebServlet("/users/create")
public class CreateServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public CreateServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").include(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        
        HttpSession session = request.getSession();
        
        if (username.equals("") || password.equals("") || !password.equals(confirmPassword)) {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Invalid Username or Password!"));
            request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").include(request, response);
            return;
        }
        
        User user = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        boolean isSuccess = false;
        
        try {
            Connection conn = ds.getConnection();
            
            user = new User();
            user.setUsername(username);
            
            byte[] saltBytes = Util.getSalt();
            String salt = Util.bytesToString(saltBytes);
            byte[] hashedPasswordBytes = Util.hashPassword(password, saltBytes);
            String hashedPassword = Util.bytesToString(hashedPasswordBytes);
            
            user.setHashed_password(hashedPassword);
            user.setSalt(salt);
            
            UsersDAO usersDAO = new UsersDAO(conn);
            isSuccess = usersDAO.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "สร้างผู้ใช้สำเร็จ :D"));
            response.sendRedirect("/");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "สร้างผู้ใช้ไม่สำเร็จ D:"));
            request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").include(request, response);
        }
    }
    
}
