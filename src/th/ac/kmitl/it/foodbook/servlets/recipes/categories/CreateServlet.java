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

@WebServlet("/recipes/categories/create")
public class CreateServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public CreateServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.getRequestDispatcher("/WEB-INF/views/recipes/categories/create.jsp").include(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        
        RecipeCategory recipeCategory = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        boolean isSuccess = false;
        
        try {
            Connection conn = ds.getConnection();
            
            recipeCategory = new RecipeCategory();
            recipeCategory.setName(name);
            
            RecipeCategoriesDAO recipeCategoriesDAO = new RecipeCategoriesDAO(conn);
            
            isSuccess = recipeCategoriesDAO.create(recipeCategory);
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        HttpSession session = request.getSession();
        
        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Created Successfully!"));
            response.sendRedirect("/recipes/categories/index");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Created Unsuccessfully!"));
            request.getRequestDispatcher("/WEB-INF/views/recipes/categories/create.jsp").include(request, response);
        }
    }
    
}
