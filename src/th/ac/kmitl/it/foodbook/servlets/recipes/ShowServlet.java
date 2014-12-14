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

import th.ac.kmitl.it.foodbook.beans.Ingredient;
import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.RecipeStep;
import th.ac.kmitl.it.foodbook.beans.RecipeStepPhoto;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.IngredientsDAO;
import th.ac.kmitl.it.foodbook.daos.RatesDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeStepPhotosDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeStepsDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;
import th.ac.kmitl.it.foodbook.daos.UsersDAO;

@WebServlet("/recipes/show")
public class ShowServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public ShowServlet() {
        super();
       
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String recipeIdString = request.getParameter("id");
        long recipeId = Long.parseLong(recipeIdString);
        
        Recipe recipe = null;
        User recipeUser = null;
        String rate = null;
        List<Ingredient> ingredients = null;
        List<RecipeStep> recipeSteps = null;
        List<List<RecipeStepPhoto>> recipeStepPhotos = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        try {
            Connection conn = ds.getConnection();
            
            RecipesDAO recipesDAO = new RecipesDAO(conn);
            recipe = recipesDAO.find(recipeId);
            
            UsersDAO usersDAO = new UsersDAO(conn);
            recipeUser = usersDAO.find(recipe.getUser_id());
            
            RatesDAO ratesDAO = new RatesDAO(conn);
            rate = String.format("%.2f", ratesDAO.average(recipeId));
            
            IngredientsDAO ingredientsDAO = new IngredientsDAO(conn);
            ingredients = ingredientsDAO.findByRecipeId(recipeId);
            
            RecipeStepsDAO recipeStepsDAO = new RecipeStepsDAO(conn);
            recipeSteps = recipeStepsDAO.findByRecipeId(recipeId);
            
            RecipeStepPhotosDAO recipeStepPhotosDAO = new RecipeStepPhotosDAO(conn);
            recipeStepPhotos = new ArrayList<List<RecipeStepPhoto>>();
            
            for (RecipeStep recipeStep : recipeSteps) {
                List<RecipeStepPhoto> recipeStepPhoto = recipeStepPhotosDAO.findByRecipeStepId(recipeStep.getRecipe_step_id());
                recipeStepPhotos.add(recipeStepPhoto);
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        request.setAttribute("recipe", recipe);
        request.setAttribute("recipeUser", recipeUser);
        request.setAttribute("rate", rate);
        request.setAttribute("ingredients", ingredients);
        request.setAttribute("recipeSteps", recipeSteps);
        request.setAttribute("recipeStepPhotos", recipeStepPhotos);
        
        request.getRequestDispatcher("/WEB-INF/views/recipes/show.jsp").include(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
    
}
