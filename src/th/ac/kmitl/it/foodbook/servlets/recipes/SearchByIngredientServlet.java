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
        List<Recipe> recipesPartial = null;
        List<List<Ingredient>> ingredientsPartial = null;
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
            recipesPartial = new ArrayList<Recipe>();
            ingredientsPartial = new ArrayList<List<Ingredient>>();
            
            // For ingredient in all selected ingredients...
            for (String ingredientIdString : ingredientIds) {
                int ingredientId = Integer.parseInt(ingredientIdString);
                // Get recipes that have said ingredient in its ingredients.
                List<Recipe> tempRecipes = recipesDAO.findByIngredientId(ingredientId);
                
                // For recipe in all recipes that have said ingredient in its ingredients.
                for (Recipe recipe : tempRecipes) {
                    // Get ingredients that used by said recipe.
                    List<Ingredient> ingredients = ingredientsDAO.findByRecipeId(recipe.getRecipe_id());
                    // Create a set for said ingredients.
                    Set<String> recipesIngredientIdStrings = new HashSet<String>();
                    
                    // For ingredient in said ingredients...
                    for (Ingredient ingredient : ingredients) {
                        // Add it to said set.
                        recipesIngredientIdStrings.add(String.valueOf(ingredient.getIngredient_id()));
                    }
                    
                    // if said set is a subset of selected ingredients. (this recipe can be cooked)
                    if (ingredientIdStrings.containsAll(recipesIngredientIdStrings)) {
                        
                        // TODO: Remove fuck up (duplicate recipe).
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
                    
                    if (recipesIngredientIdStrings.containsAll(ingredientIdStrings)) {

                        // TODO: Remove fuck up (duplicate recipe).
                        boolean fuckedUp = false;
                        
                        for (Recipe fu : recipes) {
                            if (recipe.getRecipe_id() == fu.getRecipe_id()) {
                                fuckedUp = true;
                            }
                        }
                        
                        if (!fuckedUp) {
                            recipesPartial.add(recipe);
                            
                            List<Ingredient> ingredientPartial = new ArrayList<Ingredient>();
                            
                            System.out.println(ingredientIdStrings);
                            System.out.println(recipesIngredientIdStrings);
                            
                            for (String recipeIngredientPartialIdString : recipesIngredientIdStrings.toArray(new String[ingredientIdStrings.size()])) {
                                
                                if (!ingredientIdStrings.contains(recipeIngredientPartialIdString)) {
                                    
                                    long ingredientPartialId = Long.parseLong(recipeIngredientPartialIdString);
                                    Ingredient ingredient = ingredientsDAO.find(ingredientPartialId);
                                    
                                    ingredientPartial.add(ingredient);
                                }
                            }
                            ingredientsPartial.add(ingredientPartial);
                        }
                        
                    }
                }
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
        }
        
        System.out.println();
        
        for (int i = 0; i < recipesPartial.size(); i++) {
            System.out.println(recipesPartial.get(i));
            
            for (Ingredient ingredient : ingredientsPartial.get(i)) {
                System.out.println("\t"+ingredient);
            }
        }
        
        request.setAttribute("recipes", recipes);
        request.setAttribute("recipesPartial", recipesPartial);
        request.setAttribute("ingredientsPartial", ingredientsPartial);
        request.setAttribute("recipeCategories", recipeCategories);
        
        request.getRequestDispatcher("/WEB-INF/views/recipes/search-by-ingredient.jsp").include(request, response);
    }
    
}
