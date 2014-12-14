package th.ac.kmitl.it.foodbook.servlets.ingredients.categories;

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

import th.ac.kmitl.it.foodbook.beans.IngredientCategory;
import th.ac.kmitl.it.foodbook.daos.IngredientCategoriesDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/ingredients/categories/create")
public class CreateServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public CreateServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/ingredients/categories/create.jsp").include(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        
        IngredientCategory ingredientCategory = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        boolean isSuccess = false;
        
        try {
            Connection conn = ds.getConnection();
            
            IngredientCategoriesDAO ingredientCategoriesDAO = new IngredientCategoriesDAO(conn);
            
            ingredientCategory = new IngredientCategory();
            ingredientCategory.setName(name);
            
            isSuccess = ingredientCategoriesDAO.create(ingredientCategory);
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        HttpSession session = request.getSession();
        
        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Created Successfully!"));
            response.sendRedirect("/ingredients/categories/create");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Created Unsuccessfully!"));
            request.getRequestDispatcher("/WEB-INF/views/ingredients/categories/create.jsp").include(request, response);
        }
    }
    
}
