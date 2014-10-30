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

import sun.reflect.generics.visitor.Reifier;
import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.RecipeStep;
import th.ac.kmitl.it.foodbook.beans.RecipeStepPhoto;
import th.ac.kmitl.it.foodbook.beans.User;
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
    	request.getRequestDispatcher("/WEB-INF/views/recipes/create.jsp").include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String name = request.getParameter("name");
    	String videoUrl = request.getParameter("video_url");
    	
    	String[] stepTitles = request.getParameterValues("step_title");
    	String[] stepDescriptions = request.getParameterValues("step_description");
    	String[] stepPhotoUrls = request.getParameterValues("step_photo_url");
    	
    	Recipe recipe = null;
    	
    	DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
		
		boolean isSuccess = false;
    	
    	try {
			Connection conn = ds.getConnection();
			RecipesDAO recipesDAO = new RecipesDAO(conn);
			
			recipe = new Recipe();
			
			recipe.setName(name);
			recipe.setVideo_url(videoUrl);
			
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			recipe.setUser_id(user.getUser_id());
			
			if (recipesDAO.create(recipe)) {
				System.out.println("Rid"+recipe.getRecipe_id());
				isSuccess = true;
				
				RecipeStepsDAO recipeStepsDAO = new RecipeStepsDAO(conn);
				RecipeStepPhotosDAO recipeStepPhotosDAO = new RecipeStepPhotosDAO(conn);
				
				for (int i = 0; i < stepTitles.length; i++) {
					RecipeStep recipeStep = new RecipeStep();
					recipeStep.setTitle(stepTitles[i]);
					recipeStep.setDescription(stepDescriptions[i]);
					recipeStep.setRecipe_id(recipe.getRecipe_id());
					
					if (recipeStepsDAO.create(recipeStep)) {
						System.out.println("RSid"+recipeStep.getRecipe_step_id());
						RecipeStepPhoto recipeStepPhoto = new RecipeStepPhoto();
						recipeStepPhoto.setPhoto_url(stepPhotoUrls[i]);
						recipeStepPhoto.setRecipe_step_id(recipeStep.getRecipe_step_id());
						recipeStepPhotosDAO.create(recipeStepPhoto);
						System.out.println("RSPid"+recipeStepPhoto.getRecipe_step_photo_id());
					} else {
						isSuccess = false;
						break;
					}
				}
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(500);
		}
		
		HttpSession session = request.getSession();
    	
    	if (isSuccess) {
    		session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Created Successfully!"));
    		response.sendRedirect("/recipes/show?id=" + recipe.getRecipe_id());
    	} else {
    		session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Created Unsuccessfully!"));
    		request.getRequestDispatcher("/WEB-INF/views/recipes/create.jsp").include(request, response);
    	}
    }

}
