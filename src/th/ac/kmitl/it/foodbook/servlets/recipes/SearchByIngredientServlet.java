package th.ac.kmitl.it.foodbook.servlets.recipes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
import th.ac.kmitl.it.foodbook.beans.Kitchenware;
import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.RecipeCategory;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.IngredientsDAO;
import th.ac.kmitl.it.foodbook.daos.KitchenwaresDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeCategoriesDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;
import th.ac.kmitl.it.foodbook.daos.UsersDAO;

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
        
        if (ingredientIds == null) {
            response.sendRedirect("/");
            return;
        }
        
        List<Recipe> recipes = null;
        List<User> recipesUsers = null;
        List<Recipe> recipesPartial = null;
        List<User> recipesPartialUsers = null;
        List<List<Ingredient>> ingredientsPartial = null;
        List<RecipeCategory> recipeCategories = null;
        List<Kitchenware> kitchenwares = null;
        List<List<Kitchenware>> recipesKitchenwares = null;
        List<List<Kitchenware>> recipesKitchenwaresPartial = null;
        List<List<RecipeCategory>> recipesCategories = null;
        List<List<RecipeCategory>> recipesCategoriesPartial = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        try {
            Connection conn = ds.getConnection();
            
            RecipeCategoriesDAO recipeCategoriesDAO = new RecipeCategoriesDAO(conn);
            recipeCategories = recipeCategoriesDAO.findAll();
            
            KitchenwaresDAO kitchenwaresDAO = new KitchenwaresDAO(conn);
            kitchenwares = kitchenwaresDAO.findAll();
            
            Set<String> ingredientIdStrings = new HashSet<String>(Arrays.asList(ingredientIds));
            
            IngredientsDAO ingredientsDAO = new IngredientsDAO(conn);
            RecipesDAO recipesDAO = new RecipesDAO(conn);
            UsersDAO usersDAO = new UsersDAO(conn);
            
            recipes = new ArrayList<Recipe>();
            recipesUsers = new ArrayList<User>();
            recipesPartial = new ArrayList<Recipe>();
            recipesPartialUsers = new ArrayList<User>();
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

                    System.out.println(ingredientIdStrings);
                    System.out.println(recipesIngredientIdStrings);
                    System.out.println(ingredientIdStrings.containsAll(recipesIngredientIdStrings));
                    System.out.println();
                    
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
                    
                    boolean found = false;
                    
                    for (String ingredientIdA : recipesIngredientIdStrings) {
                        if (ingredientIdStrings.contains(ingredientIdA)) {
                            found = true;
                            break;
                        }
                    }
                    
                    if (found || recipesIngredientIdStrings.containsAll(ingredientIdStrings)) {

                        // TODO: Remove fuck up (duplicate recipe).
                        boolean fuckedUp = false;
                        
                        for (Recipe fu : recipesPartial) {
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
            
            Collections.sort(recipes, new Comparator<Recipe>() {
                @Override
                public int compare(Recipe o1, Recipe o2) {
                    return o1.getAverageRate() < o2.getAverageRate() ? 1 : -1;
                }
            });
            
            recipesCategories = new ArrayList<List<RecipeCategory>>();
            recipesKitchenwares = new ArrayList<List<Kitchenware>>();
            
            for (Recipe recipe : recipes) {
                User user = usersDAO.find(recipe.getUser_id());
                recipesUsers.add(user);
                
                List<RecipeCategory> aRecipeCategories = recipeCategoriesDAO.findByRecipeId(recipe.getRecipe_id());
                recipesCategories.add(aRecipeCategories);
                
                List<Kitchenware> recipesKitchenware = kitchenwaresDAO.findByRecipeId(recipe.getRecipe_id());
                recipesKitchenwares.add(recipesKitchenware);
            }
            
            Collections.sort(recipesPartial, new Comparator<Recipe>() {
                @Override
                public int compare(Recipe o1, Recipe o2) {
                    return o1.getAverageRate() < o2.getAverageRate() ? 1 : -1;
                }
            });

            recipesCategoriesPartial = new ArrayList<List<RecipeCategory>>();
            recipesKitchenwaresPartial = new ArrayList<List<Kitchenware>>();
            
            for (Recipe recipePartial : recipesPartial) {
                User user = usersDAO.find(recipePartial.getUser_id());
                recipesPartialUsers.add(user);
                
                List<RecipeCategory> aRecipeCategories = recipeCategoriesDAO.findByRecipeId(recipePartial.getRecipe_id());
                recipesCategoriesPartial.add(aRecipeCategories);
                
                List<Kitchenware> recipesKitchenware = kitchenwaresDAO.findByRecipeId(recipePartial.getRecipe_id());
                recipesKitchenwaresPartial.add(recipesKitchenware);
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
        request.setAttribute("recipesCategories", recipesCategories);
        request.setAttribute("recipesUsers", recipesUsers);
        request.setAttribute("recipesPartial", recipesPartial);
        request.setAttribute("recipesCategoriesPartial", recipesCategoriesPartial);
        request.setAttribute("recipesPartialUsers", recipesPartialUsers);
        request.setAttribute("ingredientsPartial", ingredientsPartial);
        request.setAttribute("recipeCategories", recipeCategories);
        request.setAttribute("kitchenwares", kitchenwares);
        request.setAttribute("recipesKitchenwares", recipesKitchenwares);
        request.setAttribute("recipesKitchenwaresPartial", recipesKitchenwaresPartial);
        
        request.getRequestDispatcher("/WEB-INF/views/recipes/search-by-ingredient.jsp").include(request, response);
    }
    
}
