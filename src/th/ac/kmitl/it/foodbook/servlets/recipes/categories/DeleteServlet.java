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
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/recipes/categories/delete")
public class DeleteServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public DeleteServlet() {
        super();
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] recipeCategoryIdString = request.getParameterValues("recipe_category_id");
        long recipeCategoryId;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        boolean isSuccess = false;
        boolean isDelete = false;
        boolean isRemoveAllRecipeCategory = false;
        
        HttpSession session = request.getSession();
        
        try {
            Connection conn = ds.getConnection();
            
            for (String i : recipeCategoryIdString) {
                recipeCategoryId = Long.parseLong(i);
                RecipeCategory recipeCategory = new RecipeCategory();
                recipeCategory.setRecipe_category_id(recipeCategoryId);
                RecipeCategoriesDAO recipeCategoriesDAO = new RecipeCategoriesDAO(conn);
                RecipesDAO recipesDAO = new RecipesDAO(conn);
                isRemoveAllRecipeCategory = recipesDAO.removeAllRecipeCategory(recipeCategoryId);
                isDelete = recipeCategoriesDAO.delete((recipeCategory));
                isSuccess = isDelete;
             
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Deleted Successfully!"));
            response.sendRedirect("/");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Deleted Unsuccessfully!"));
            response.sendRedirect("/recipes/categories/index");
        }
    }
}
