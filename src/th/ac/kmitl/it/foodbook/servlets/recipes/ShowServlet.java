package th.ac.kmitl.it.foodbook.servlets.recipes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
		List<Ingredient> ingredients = null;
		
		DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");

		try {
			Connection conn = ds.getConnection();
			RecipesDAO recipesDAO = new RecipesDAO(conn);

			recipe = recipesDAO.find(recipeId);
			
			IngredientsDAO ingredientsDAO = new IngredientsDAO(conn);
			
			ingredients = ingredientsDAO.findByRecipeId(recipeId);
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(500);
		}

		request.setAttribute("recipe", recipe);
		request.setAttribute("ingredients", ingredients);
		request.getRequestDispatcher("/WEB-INF/views/recipes/show.jsp").include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
