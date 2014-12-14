package th.ac.kmitl.it.foodbook.servlets.favorites;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.Favorite;
import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.RecipeCategory;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.FavoritesDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeCategoriesDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;

@WebServlet("/favorites/index")
public class IndexServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public IndexServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Favorite> favorites = null;
        List<Recipe> recipes = new ArrayList<Recipe>();
        List<RecipeCategory> recipeCategories = null;
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        try {
            Connection conn = ds.getConnection();
            
            FavoritesDAO favoritesDAO = new FavoritesDAO(conn);
            favorites = favoritesDAO.findByUserId(user.getUser_id());
            
            RecipesDAO recipesDAO = new RecipesDAO(conn);
            
            RecipeCategoriesDAO recipeCategoriesDAO = new RecipeCategoriesDAO(conn);
            recipeCategories = recipeCategoriesDAO.findAll();
            
            for (Favorite favorite : favorites) {
                Recipe recipe = recipesDAO.find(favorite.getRecipe_id());
                recipes.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        request.setAttribute("recipes", recipes);
        request.setAttribute("recipeCategories", recipeCategories);
        request.getRequestDispatcher("/WEB-INF/views/favorites/index.jsp").include(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
