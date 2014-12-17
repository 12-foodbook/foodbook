package th.ac.kmitl.it.foodbook.servlets.moderators;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.Moderator;
import th.ac.kmitl.it.foodbook.daos.ModeratorsDAO;

@WebServlet("/moderators/index")
public class IndexServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public IndexServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Moderator> moderators = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        try {
            Connection conn = ds.getConnection();
            
            ModeratorsDAO moderatorsDAO = new ModeratorsDAO(conn);
            moderators = moderatorsDAO.findAll();
            
            for(Moderator i:moderators){
                System.out.println(i.getUsername());
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        request.setAttribute("moderators", moderators);
        request.getRequestDispatcher("/WEB-INF/views/moderators/index.jsp").include(request, response);
    }
    
}
