package th.ac.kmitl.it.foodbook.servlets.recipes.categories;

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

import th.ac.kmitl.it.foodbook.beans.RecipeCategory;
import th.ac.kmitl.it.foodbook.daos.RecipeCategoriesDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/recipes/categories/edit")
public class EditServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public EditServlet() {
        super();
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long recipeCategoryId = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        
        HttpSession session = request.getSession();
        
        RecipeCategory recipeCategory = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        boolean isSuccess = false;
        
        try {
            Connection conn = ds.getConnection();
            
            RecipeCategoriesDAO recipeCategoriesDAO = new RecipeCategoriesDAO(conn);
            
            recipeCategory = recipeCategoriesDAO.find(recipeCategoryId);
            recipeCategory.setName(name);
            
            if (recipeCategoriesDAO.update(recipeCategory)) {
                isSuccess = true;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
            return;
        }
        
        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Edited Successfully!"));
            response.sendRedirect("/recipes/categories/index.jsp");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Edited Unsuccessfully!"));
            request.getRequestDispatcher("/WEB-INF/views/recipes/categories/index.jsp").include(request, response);
        }
    }
    
}
