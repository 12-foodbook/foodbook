package th.ac.kmitl.it.foodbook.servlets.recipes;

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
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.Kitchenware;
import th.ac.kmitl.it.foodbook.beans.Moderator;
import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.RecipeCategory;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.KitchenwaresDAO;
import th.ac.kmitl.it.foodbook.daos.ModeratorsDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeCategoriesDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;
import th.ac.kmitl.it.foodbook.daos.UsersDAO;

@WebServlet("/recipes/index")
public class IndexServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public IndexServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Recipe> recipes = null;
        List<RecipeCategory> recipeCategories = null;
        List<Kitchenware> kitchenwares = null;
        List<List<Kitchenware>> recipesKitchenwares = null;
        List<List<RecipeCategory>> recipesCategories = null;
        List<Moderator> recipesModerators = null;
        List<User> recipesUsers = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        try {
            Connection conn = ds.getConnection();
            
            RecipesDAO recipesDAO = new RecipesDAO(conn);
            recipes = recipesDAO.findAll();
            
            RecipeCategoriesDAO recipeCategoriesDAO = new RecipeCategoriesDAO(conn);
            recipeCategories = recipeCategoriesDAO.findAll();
            
            UsersDAO usersDAO = new UsersDAO(conn);
            ModeratorsDAO moderatorsDAO = new ModeratorsDAO(conn);
            
            KitchenwaresDAO kitchenwaresDAO = new KitchenwaresDAO(conn);
            kitchenwares = kitchenwaresDAO.findAll();
            
            recipesCategories = new ArrayList<List<RecipeCategory>>();
            recipesModerators = new ArrayList<Moderator>();
            recipesUsers = new ArrayList<User>();
            recipesKitchenwares = new ArrayList<List<Kitchenware>>();
            
            for (Recipe recipe : recipes) {
                List<RecipeCategory> aRecipesCategories = recipeCategoriesDAO.findByRecipeId(recipe.getRecipe_id());
                recipesCategories.add(aRecipesCategories);

                if (recipe.getIs_moderator_id()) {
                    Moderator moderator = moderatorsDAO.find(recipe.getUser_id());
                    recipesModerators.add(moderator);
                } else {
                    User user = usersDAO.find(recipe.getUser_id());
                    recipesUsers.add(user);
                }
                
                List<Kitchenware> aRecipesKitchenware = kitchenwaresDAO.findByRecipeId(recipe.getRecipe_id());
                recipesKitchenwares.add(aRecipesKitchenware);
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        request.setAttribute("recipes", recipes);
        request.setAttribute("recipeCategories", recipeCategories);
        request.setAttribute("recipesCategories", recipesCategories);
        request.setAttribute("kitchenwares", kitchenwares);
        request.setAttribute("recipesKitchenwares", recipesKitchenwares);
        request.setAttribute("recipesUsers", recipesUsers);
        request.setAttribute("recipesModerators", recipesModerators);
        request.getRequestDispatcher("/WEB-INF/views/recipes/index.jsp").include(request, response);
    }
    
}
