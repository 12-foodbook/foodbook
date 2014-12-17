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

@WebServlet("/users/change-password")
public class ChangePasswordServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public ChangePasswordServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/users/change-password.jsp").include(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oldPassword = request.getParameter("old-password");
        String newPassword = request.getParameter("new-password");
        String confirmPassword = request.getParameter("confirm-password");
        
        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("user");
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        try {
            Connection conn = ds.getConnection();
            
            UsersDAO usersDAO = new UsersDAO(conn);
            
            System.out.println(user.getUsername());
            System.out.println(oldPassword);
            System.out.println(usersDAO.authenticate(user.getUsername(), oldPassword));
            
            if (usersDAO.authenticate(user.getUsername(), oldPassword) == null) {
                session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Access Denial D:"));
                response.sendRedirect("/");
                return;
            }
            
            if (!newPassword.equals(confirmPassword)) {
                session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Password!"));
                request.getRequestDispatcher("/WEB-INF/views/users/change-password.jsp").include(request, response);
                return;
            }
            
            String newSalt = Util.bytesToString(Util.getSalt());
            String newHashedPassword = Util.bytesToString(Util.hashPassword(newPassword, Util.stringToBytes(newSalt)));

            user.setSalt(newSalt);
            user.setHashed_password(newHashedPassword);
            boolean isSuccess = usersDAO.update(user);
            
            if (isSuccess) {
                session.setAttribute("user", user);
                
                session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Changed Password Successfully!"));
                response.sendRedirect("/");
            } else {
                session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Changed Password Unsuccessfully!"));
                response.sendRedirect("/");
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
    
}
