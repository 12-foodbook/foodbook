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
import th.ac.kmitl.it.foodbook.utils.Util;

@WebServlet("/moderators/change-password")
public class ChangePasswordServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public ChangePasswordServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/moderators/change-password.jsp").include(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oldPassword = request.getParameter("old-password");
        String newPassword = request.getParameter("new-password");
        String confirmPassword = request.getParameter("confirm-password");
        
        HttpSession session = request.getSession();
        
        Moderator moderator = (Moderator) session.getAttribute("moderator");
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        try {
            Connection conn = ds.getConnection();
            
            ModeratorsDAO moderatorsDAO = new ModeratorsDAO(conn);
            
            System.out.println(moderator.getUsername());
            System.out.println(oldPassword);
            System.out.println(moderatorsDAO.authenticate(moderator.getUsername(), oldPassword));
            
            if (moderatorsDAO.authenticate(moderator.getUsername(), oldPassword) == null) {
                session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Access Denial D:"));
                response.sendRedirect("/");
                return;
            }
            
            if (!newPassword.equals(confirmPassword)) {
                session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Password!"));
                request.getRequestDispatcher("/WEB-INF/views/moderators/change-password.jsp").include(request, response);
                return;
            }
            
            String newSalt = Util.bytesToString(Util.getSalt());
            String newHashedPassword = Util.bytesToString(Util.hashPassword(newPassword, Util.stringToBytes(newSalt)));

            moderator.setSalt(newSalt);
            moderator.setHashed_password(newHashedPassword);
            boolean isSuccess = moderatorsDAO.update(moderator);
            
            if (isSuccess) {
                session.setAttribute("moderator", moderator);
                
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
