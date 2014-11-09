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
import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.RecipeStep;
import th.ac.kmitl.it.foodbook.beans.RecipeStepPhoto;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.IngredientCategoriesDAO;
import th.ac.kmitl.it.foodbook.daos.IngredientsDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeStepPhotosDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeStepsDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/recipes/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateServlet() {
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<IngredientCategory> ingredientCategories = null;
    	List<List<Ingredient>> ingredients = null;
    	
    	DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
    	
    	try {
    		Connection conn = ds.getConnection();
	    	IngredientCategoriesDAO ingredientCategoriesDAO = new IngredientCategoriesDAO(conn);
	    	
	    	ingredientCategories = ingredientCategoriesDAO.findAll();
	    	
	    	IngredientsDAO ingredientsDAO = new IngredientsDAO(conn);
	    	
	    	ingredients = new ArrayList<List<Ingredient>>();
	    	
	    	for (IngredientCategory ingredientCategory : ingredientCategories) {
	    		List<Ingredient> tempIngredients = ingredientsDAO.findByIngredientCategoryId(ingredientCategory.getIngredient_category_id());
	    		ingredients.add(tempIngredients);
	    	}
	    	
	    	conn.close();
    	} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(500);
		}
    	
    	request.setAttribute("ingredientCategories", ingredientCategories);
    	request.setAttribute("ingredients", ingredients);
    	
    	request.getRequestDispatcher("/WEB-INF/views/recipes/create.jsp").include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String name = request.getParameter("name");
    	String videoUrl = request.getParameter("video_url");
    	String[] ingredientIds = request.getParameterValues("ingredient_id");
    	String[] ingredientAmounts = request.getParameterValues("ingredient_amount");
    	
    	String[] stepTitles = request.getParameterValues("step_title");
    	String[] stepDescriptions = request.getParameterValues("step_description");
    	String[] stepPhotoUrls = request.getParameterValues("step_photo_url");
		
		HttpSession session = request.getSession();
		
		if (name.length() == 0 ||
			ingredientIds.length == 0 ||
			ingredientAmounts.length == 0 ||
			stepTitles.length == 0
		) {
			session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Invalid Inputs!"));
    		request.getRequestDispatcher("/WEB-INF/views/recipes/create.jsp").include(request, response);
		}
    	
    	Recipe recipe = null;
    	
    	DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
		
		boolean isSuccess = false;
    	
    	try {
			Connection conn = ds.getConnection();
			RecipesDAO recipesDAO = new RecipesDAO(conn);
			
			recipe = new Recipe();
			
			recipe.setName(name);
			recipe.setVideo_url(videoUrl);
			
			User user = (User) session.getAttribute("user");
			
			recipe.setUser_id(user.getUser_id());
			
			if (recipesDAO.create(recipe)) {
				isSuccess = true;
				
				for (int i = 0; i < ingredientIds.length; i++) {
					long ingredientId = Integer.parseInt(ingredientIds[i]);
					
					if (!recipesDAO.createIngredient(recipe.getRecipe_id(), ingredientId, ingredientAmounts[i])) {
						isSuccess = false;
						return;
					}
				}
				
				RecipeStepsDAO recipeStepsDAO = new RecipeStepsDAO(conn);
				RecipeStepPhotosDAO recipeStepPhotosDAO = new RecipeStepPhotosDAO(conn);
				
				for (int i = 0; i < stepTitles.length; i++) {
					RecipeStep recipeStep = new RecipeStep();
					recipeStep.setTitle(stepTitles[i]);
					recipeStep.setDescription(stepDescriptions[i]);
					recipeStep.setRecipe_id(recipe.getRecipe_id());
					
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
		}
    	
    	if (isSuccess) {
    		session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Created Successfully!"));
    		response.sendRedirect("/recipes/show?id=" + recipe.getRecipe_id());
    	} else {
    		session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Created Unsuccessfully!"));
    		request.getRequestDispatcher("/WEB-INF/views/recipes/create.jsp").include(request, response);
    	}
    }

}
