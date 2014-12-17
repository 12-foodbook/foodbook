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

import th.ac.kmitl.it.foodbook.daos.ModeratorsDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/moderators/delete")
public class DeleteServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public DeleteServlet() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] moderatorIdsString = request.getParameterValues("moderator_id");
        
        boolean isSuccess = false;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        HttpSession session = request.getSession();
        
        try {
            Connection conn = ds.getConnection();
            
            if (moderatorIdsString == null) {
                response.sendRedirect("/moderators/index");
                return;
            }
            
            for (String moderatorIdString : moderatorIdsString) {
                long moderatorId = Long.parseLong(moderatorIdString);
                
                ModeratorsDAO moderatorsDAO = new ModeratorsDAO(conn);
                isSuccess = moderatorsDAO.delete(moderatorId);
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "≈∫ºŸÈ¥Ÿ·≈ ”‡√Á® :D"));
            response.sendRedirect("/moderators/index");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "≈∫ºŸÈ¥Ÿ·≈‰¡Ë ”‡√Á® D:"));
            response.sendRedirect("/moderators/index");
        }
    }
    
}
