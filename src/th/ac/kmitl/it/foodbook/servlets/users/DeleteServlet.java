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

import th.ac.kmitl.it.foodbook.daos.UsersDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/users/delete")
public class DeleteServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public DeleteServlet() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] userIdsString = request.getParameterValues("user_id");
        
        boolean isSuccess = false;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        HttpSession session = request.getSession();
        
        try {
            Connection conn = ds.getConnection();
            
            if (userIdsString == null) {
                response.sendRedirect("/users/index");
                return;
            }
            
            for (String userIdString : userIdsString) {
                long userId = Long.parseLong(userIdString);
                
                UsersDAO usersDAO = new UsersDAO(conn);
                isSuccess = usersDAO.delete(userId);
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "ลบผู้ใช้สำเร็จ :D"));
            response.sendRedirect("/users/index");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "ลบผู้ใช้ไม่สำเร็จ D:"));
            response.sendRedirect("/users/index");
        }
    }
    
}
