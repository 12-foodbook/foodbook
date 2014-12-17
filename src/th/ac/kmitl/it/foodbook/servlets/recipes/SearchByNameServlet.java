package th.ac.kmitl.it.foodbook.servlets.recipes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.Kitchenware;
import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.RecipeCategory;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.KitchenwaresDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeCategoriesDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;
import th.ac.kmitl.it.foodbook.daos.UsersDAO;

@WebServlet("/recipes/search-by-name")
public class SearchByNameServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public SearchByNameServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        
        List<Recipe> recipes = null;
        List<User> recipesUsers = null;
        List<RecipeCategory> recipeCategories = null;
        List<List<RecipeCategory>> recipesCategories = null;
        List<Kitchenware> kitchenwares = null;
        List<List<Kitchenware>> recipesKitchenwares = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        try {
            Connection conn = ds.getConnection();
            
            RecipesDAO recipesDAO = new RecipesDAO(conn);
            recipes = recipesDAO.findByNameLike(query);
            
            RecipeCategoriesDAO recipeCategoriesDAO = new RecipeCategoriesDAO(conn);
            recipeCategories = recipeCategoriesDAO.findAll();
            
            Collections.sort(recipes, new Comparator<Recipe>() {
                @Override
                public int compare(Recipe o1, Recipe o2) {
                    return o1.getAverageRate() < o2.getAverageRate() ? 1 : -1;
                }
            });
            
            KitchenwaresDAO kitchenwaresDAO = new KitchenwaresDAO(conn);
            kitchenwares = kitchenwaresDAO.findAll();

            UsersDAO usersDAO = new UsersDAO(conn);
            recipesUsers = new ArrayList<User>();
            
            recipesCategories = new ArrayList<List<RecipeCategory>>();
            recipesKitchenwares = new ArrayList<List<Kitchenware>>();
            
            for (Recipe recipe : recipes) {
                User user = usersDAO.find(recipe.getUser_id());
                recipesUsers.add(user);
                
                List<RecipeCategory> aRecipesCategories = recipeCategoriesDAO.findByRecipeId(recipe.getRecipe_id());
                recipesCategories.add(aRecipesCategories);
                
                List<Kitchenware> recipesKitchenware = kitchenwaresDAO.findByRecipeId(recipe.getRecipe_id());
                recipesKitchenwares.add(recipesKitchenware);
            }
            
            for (List<RecipeCategory> aaRecipeCategories : recipesCategories) {
                System.out.println(aaRecipeCategories);
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        request.setAttribute("recipes", recipes);
        request.setAttribute("recipesCategories", recipesCategories);
        request.setAttribute("recipesUsers", recipesUsers);
        request.setAttribute("recipeCategories", recipeCategories);
        request.setAttribute("recipesKitchenwares", recipesKitchenwares);
        request.setAttribute("kitchenwares", kitchenwares);
        
        request.getRequestDispatcher("/WEB-INF/views/recipes/search-by-name.jsp").include(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
