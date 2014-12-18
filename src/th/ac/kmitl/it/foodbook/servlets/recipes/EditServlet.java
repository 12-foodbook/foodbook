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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.Ingredient;
import th.ac.kmitl.it.foodbook.beans.IngredientCategory;
import th.ac.kmitl.it.foodbook.beans.Kitchenware;
import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.RecipeCategory;
import th.ac.kmitl.it.foodbook.beans.RecipeStep;
import th.ac.kmitl.it.foodbook.beans.RecipeStepPhoto;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.IngredientCategoriesDAO;
import th.ac.kmitl.it.foodbook.daos.IngredientsDAO;
import th.ac.kmitl.it.foodbook.daos.KitchenwaresDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeCategoriesDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeStepPhotosDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeStepsDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/recipes/edit")
public class EditServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public EditServlet() {
        super();
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String recipeIdString = request.getParameter("id");
        long recipeId = Long.parseLong(recipeIdString);
        
        Recipe recipe = null;
        
        List<RecipeCategory> recipeCategories = null;
        List<Ingredient> recipeIngredients = null;
        List<IngredientCategory> ingredientCategories = null;
        List<List<Ingredient>> ingredients = null;
        List<RecipeStep> recipeSteps = null;
        List<List<RecipeStepPhoto>> recipeStepPhotos = null;
        List<RecipeCategory> recipesCategories = null;
        List<Kitchenware> kitchenwaresAll = null;
        List<Kitchenware> kitchenwares = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("user");
        
        try {
            Connection conn = ds.getConnection();
            
            RecipesDAO recipesDAO = new RecipesDAO(conn);
            recipe = recipesDAO.find(recipeId);
            
            if ((user == null || recipe.getUser_id() != user.getUser_id()) && session.getAttribute("moderator") == null) {
                session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Access Denial D:"));
                response.sendRedirect("/");
                return;
            }
            
            RecipeCategoriesDAO recipeCategoriesDAO = new RecipeCategoriesDAO(conn);
            recipeCategories = recipeCategoriesDAO.findAll();
            recipesCategories = recipeCategoriesDAO.findByRecipeId(recipeId);
            
            IngredientCategoriesDAO ingredientCategoriesDAO = new IngredientCategoriesDAO(conn);
            ingredientCategories = ingredientCategoriesDAO.findAll();
            
            IngredientsDAO ingredientsDAO = new IngredientsDAO(conn);
            recipeIngredients = ingredientsDAO.findByRecipeId(recipeId);
            
            KitchenwaresDAO kitchenwaresDAO = new KitchenwaresDAO(conn);
            kitchenwaresAll = kitchenwaresDAO.findAll();
            kitchenwares = kitchenwaresDAO.findByRecipeId(recipeId);
            
            
            ingredients = new ArrayList<List<Ingredient>>();
            
            for (IngredientCategory ingredientCategory : ingredientCategories) {
                List<Ingredient> tempIngredients = ingredientsDAO.findByIngredientCategoryId(ingredientCategory.getIngredient_category_id());
                ingredients.add(tempIngredients);
            }
            
            RecipeStepsDAO recipeStepsDAO = new RecipeStepsDAO(conn);
            recipeSteps = recipeStepsDAO.findByRecipeId(recipeId);
            
            RecipeStepPhotosDAO recipeStepPhotosDAO = new RecipeStepPhotosDAO(conn);
            recipeStepPhotos = new ArrayList<List<RecipeStepPhoto>>();
            
            for (RecipeStep recipeStep : recipeSteps) {
                List<RecipeStepPhoto> recipesdlnflks = recipeStepPhotosDAO.findByRecipeStepId(recipeStep.getRecipe_step_id());
                recipeStepPhotos.add(recipesdlnflks);
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        request.setAttribute("kitchenwaresAll", kitchenwaresAll);
        request.setAttribute("kitchenwares", kitchenwares);
        request.setAttribute("recipe", recipe);
        request.setAttribute("recipeCategories", recipeCategories);
        request.setAttribute("recipeIngredients", recipeIngredients);
        request.setAttribute("ingredientCategories", ingredientCategories);
        request.setAttribute("ingredients", ingredients);
        request.setAttribute("recipeSteps", recipeSteps);
        request.setAttribute("recipesCategories", recipesCategories);
        request.setAttribute("recipeStepPhotos", recipeStepPhotos);
        
        request.getRequestDispatcher("/WEB-INF/views/recipes/edit.jsp").include(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String recipeIdString = request.getParameter("id");
        long recipeId = Long.parseLong(recipeIdString);
        
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String photoUrl = request.getParameter("photo_url");
        String videoUrl = request.getParameter("video_url");
        
        String[] kitchenwareIds = request.getParameterValues("kitchenware_id");
        String[] recipeCategoryIds = request.getParameterValues("recipe_category_id");
        
        String[] ingredientIds = request.getParameterValues("ingredient_id");
        String[] ingredientAmounts = request.getParameterValues("ingredient_amount");
        
        String[] stepTitles = request.getParameterValues("step_title");
        String[] stepDescriptions = request.getParameterValues("step_description");
        String[] stepPhotoUrls = request.getParameterValues("step_photo_url");
        
        HttpSession session = request.getSession();
        
        if (name.equals("") || ingredientIds.length == 0 || ingredientAmounts.length == 0 || stepTitles.length == 0) {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Invalid Inputs D:"));
            request.getRequestDispatcher("/WEB-INF/views/recipes/edit.jsp?id=" + recipeId).include(request, response);
            return;
        }
        
        Recipe recipe = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        boolean isSuccess = false;
        
        try {
            Connection conn = ds.getConnection();
            
            RecipesDAO recipesDAO = new RecipesDAO(conn);
            KitchenwaresDAO kitchenwaresDAO = new KitchenwaresDAO(conn);
            
            recipe = recipesDAO.find(recipeId);
            recipe.setName(name);
            recipe.setDescription(description);
            recipe.setPhoto_url(photoUrl);
            recipe.setVideo_url(videoUrl);
            
            User user = (User) session.getAttribute("user");
            
            if (recipe.getUser_id() != user.getUser_id()) {
                session.setAttribute("alert", new Alert(AlertTypes.DANGER, "User Not Found D:"));
                response.sendRedirect("/");
                return;
            }
            
            if (recipesDAO.update(recipe)) {
                isSuccess = true;
                
                recipesDAO.removeAllRecipeFromRecipeCategories(recipeId);
                
                for (String recipeCategoryIdString : recipeCategoryIds) {
                    long recipeCategoryId = Long.parseLong(recipeCategoryIdString);
                    recipesDAO.addRecipeCategory(recipe.getRecipe_id(), recipeCategoryId);
                }
                
                kitchenwaresDAO.removeAllKitchenwareFromRecipesByRecipeId(recipeId);
                
                for (String kitchenwareIdString : kitchenwareIds) {
                    long kitchenwareId = Long.parseLong(kitchenwareIdString);
                    kitchenwaresDAO.addRecipeKitchenware(recipe.getRecipe_id(), kitchenwareId);
                }
                
                for (int i = 0; i < ingredientIds.length; i++) {
                    long ingredientId = Integer.parseInt(ingredientIds[i]);
                    
                    recipesDAO.removeIngredient(recipe.getRecipe_id(), ingredientId);
                    
                    if (!recipesDAO.addIngredient(recipe.getRecipe_id(), ingredientId, Float.parseFloat(ingredientAmounts[i]))) {
                        isSuccess = false;
                        return;
                    }
                }
                
                RecipeStepsDAO recipeStepsDAO = new RecipeStepsDAO(conn);
                List<RecipeStep> recipeSteps = recipeStepsDAO.findByRecipeId(recipeId);
                
                RecipeStepPhotosDAO recipeStepPhotosDAO = new RecipeStepPhotosDAO(conn);
                
                for (RecipeStep recipeStep : recipeSteps) {
                    recipeStepPhotosDAO.deleteByRecipeStepId(recipeStep.getRecipe_step_id());
                }
                
                recipeStepsDAO.deleteByRecipeId(recipeId);
                
                for (int i = 0; i < stepTitles.length; i++) {
                    RecipeStep recipeStep = new RecipeStep();
                    recipeStep.setTitle(stepTitles[i]);
                    recipeStep.setDescription(stepDescriptions[i]);
                    recipeStep.setRecipe_id(recipeId);
                    
                    if (recipeStepsDAO.create(recipeStep)) {
                        RecipeStepPhoto recipeStepPhoto = new RecipeStepPhoto();
                        recipeStepPhoto.setPhoto_url(stepPhotoUrls[i]);
                        recipeStepPhoto.setRecipe_step_id(recipeStep.getRecipe_step_id());
                        recipeStepPhotosDAO.create(recipeStepPhoto);
                    } else {
                        isSuccess = false;
                        return;
                    }
                }
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
            return;
        }
        
        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Editted recipe successfully :D"));
            response.sendRedirect("/recipes/show?id=" + recipe.getRecipe_id());
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Editted recipe unsuccessfully D:"));
            request.getRequestDispatcher("/WEB-INF/views/recipes/edit.jsp").include(request, response);
        }
    }
    
}
