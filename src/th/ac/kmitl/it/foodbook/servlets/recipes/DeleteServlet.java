package th.ac.kmitl.it.foodbook.servlets.recipes;

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

import th.ac.kmitl.it.foodbook.daos.CommentsDAO;
import th.ac.kmitl.it.foodbook.daos.FavoritesDAO;
import th.ac.kmitl.it.foodbook.daos.RatesDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/recipes/delete")
public class DeleteServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public DeleteServlet() {
        super();
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String recipeIdString = request.getParameter("id");
        long recipeId = Long.parseLong(recipeIdString);
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        boolean isSuccess = false;
        boolean isRemoveFromRecipeCategories = false;
        boolean isRemoveFromIngredients = false;
        boolean isRemoveFromSteps = false;
        boolean isRemoveFromFavorites = false;
        boolean isRemoveFromRates = false;
        boolean isRemoveFromComments = false;
        boolean isRemove = false;
        
        HttpSession session = request.getSession();
        
        try {
            Connection conn = ds.getConnection();
            
            RecipesDAO recipesDAO = new RecipesDAO(conn);
            FavoritesDAO favoritesDAO = new FavoritesDAO(conn);
            RatesDAO ratesDAO = new RatesDAO(conn);
            CommentsDAO commentsDAO = new CommentsDAO(conn);
            isRemoveFromRecipeCategories = recipesDAO.removeAllRecipeFromRecipeCategories(recipeId);
            isRemoveFromIngredients = recipesDAO.removeAllRecipeFromIngredients(recipeId);
            isRemoveFromSteps = recipesDAO.removeAllRecipeFromSteps(recipeId);
            isRemoveFromFavorites = favoritesDAO.removeRecipe(recipeId);
            isRemoveFromRates = ratesDAO.removeRecipe(recipeId);
            isRemoveFromComments = commentsDAO.removeRecipe(recipeId);
            isRemove = recipesDAO.delete(recipeId);
            
            isSuccess = isRemoveFromRecipeCategories && isRemoveFromIngredients && isRemoveFromSteps && isRemoveFromFavorites && isRemoveFromRates && isRemoveFromComments && isRemove;
            
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
            response.sendRedirect("/recipes/index");
        }
    }
}
