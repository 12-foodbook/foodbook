package th.ac.kmitl.it.foodbook.servlets.recipes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.Ingredient;
import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.RecipeCategory;
import th.ac.kmitl.it.foodbook.daos.IngredientsDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeCategoriesDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;

@WebServlet("/recipes/search-by-ingredient")
public class SearchByIngredientServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public SearchByIngredientServlet() {
        super();
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] ingredientIds = request.getParameterValues("ingredient_id");
        
        List<Recipe> recipes = null;
        List<RecipeCategory> recipeCategories = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        try {
            Connection conn = ds.getConnection();
            
            RecipeCategoriesDAO recipeCategoriesDAO = new RecipeCategoriesDAO(conn);
            recipeCategories = recipeCategoriesDAO.findAll();
            
            Set<String> ingredientIdStrings = new HashSet<String>(Arrays.asList(ingredientIds));
            
            IngredientsDAO ingredientsDAO = new IngredientsDAO(conn);
            RecipesDAO recipesDAO = new RecipesDAO(conn);
            
            recipes = new ArrayList<Recipe>();
            
            for (String ingredientIdString : ingredientIds) {
                int ingredientId = Integer.parseInt(ingredientIdString);
                List<Recipe> tempRecipes = recipesDAO.findByIngredientId(ingredientId);
                
                for (Recipe recipe : tempRecipes) {
                    List<Ingredient> ingredients = ingredientsDAO.findByRecipeId(recipe.getRecipe_id());
                    Set<String> recipesIngredientIdStrings = new HashSet<String>();
                    
                    for (Ingredient ingredient : ingredients) {
                        recipesIngredientIdStrings.add(String.valueOf(ingredient.getIngredient_id()));
                    }
                    
                    if (ingredientIdStrings.containsAll(recipesIngredientIdStrings)) {
                        
                        // TODO: Remove fuck up.
                        boolean fuckedUp = false;
                        
                        for (Recipe fu : recipes) {
                            if (recipe.getRecipe_id() == fu.getRecipe_id()) {
                                fuckedUp = true;
                            }
                        }
                        
                        if (!fuckedUp) {
                            recipes.add(recipe);
                        }
                        
                    }
                }
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        request.setAttribute("recipes", recipes);
        request.setAttribute("recipeCategories", recipeCategories);
        
        request.getRequestDispatcher("/WEB-INF/views/recipes/search-by-ingredient.jsp").include(request, response);
    }
    
}
