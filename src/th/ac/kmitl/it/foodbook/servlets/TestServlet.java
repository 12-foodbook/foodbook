package th.ac.kmitl.it.foodbook.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
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
import th.ac.kmitl.it.foodbook.daos.IngredientsDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TestServlet() {
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/plain");
    	PrintWriter out = response.getWriter();
    	
    	DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
    	
		try {
			Connection conn = ds.getConnection();
	    	
			String[] ingredientIds = request.getParameterValues("ingredient_id");
			Set<String> shitt = new HashSet<String>(Arrays.asList(ingredientIds));
			
			IngredientsDAO ingredientsDAO = new IngredientsDAO(conn);
			
			int leastId = -1;
			int least = Integer.MAX_VALUE;
			
			for (String ingredientId : ingredientIds) {
				int id = Integer.parseInt(ingredientId);
				int count = ingredientsDAO.countRecipes(id);
				if (count < least) {
					leastId = id;
					least = count;
				}
			}
			
			out.println(leastId + ": " + least);
			
			RecipesDAO recipesDAO = new RecipesDAO(conn);
			
			List<Recipe> recipes = recipesDAO.findByIngredientId(leastId);
			
			for (Recipe recipe : recipes) {
				List<Ingredient> ingredients = ingredientsDAO.findByRecipeId(recipe.getRecipe_id());
				out.print(recipe.getRecipe_id() + ": ");
				Set<String> shit = new HashSet<String>();
				for (Ingredient ingredient : ingredients) {
					shit.add(String.valueOf(ingredient.getIngredient_id()));
					out.print(ingredient.getIngredient_id() + " ");
				}
				out.print(shit.equals(shitt));
				out.println();
			}
			
	    	conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}
