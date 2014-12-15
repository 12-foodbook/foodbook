package th.ac.kmitl.it.foodbook.servlets.kitchenwares;

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

import th.ac.kmitl.it.foodbook.beans.Kitchenware;
import th.ac.kmitl.it.foodbook.daos.KitchenwaresDAO;

@WebServlet("/kitchenwares/index")
public class IndexServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public IndexServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Kitchenware> kitchenwares = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        try {
            Connection conn = ds.getConnection();
            
            KitchenwaresDAO kitchenwaresDAO = new KitchenwaresDAO(conn);
            kitchenwares = kitchenwaresDAO.findAll();
            
            for(Kitchenware i:kitchenwares){
                System.out.println(i.getName());
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        request.setAttribute("kitchenwares", kitchenwares);
        request.getRequestDispatcher("/WEB-INF/views/kitchenwares/index.jsp").include(request, response);
    }
    
}
