package th.ac.kmitl.it.foodbook.servlets.recipes;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import th.ac.kmitl.it.foodbook.beans.Recipe;

@WebServlet("/recipes/search-by-ingredient")
public class SearchByIngredientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchByIngredientServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] ingradientIds = request.getParameterValues("ingredient_id");
		
		List<Recipe> recipes = null;
		
		
		
		request.setAttribute("recipes", recipes);
		request.getRequestDispatcher("/WEB-INF/views/recipes/index.jsp").include(request, response);
	}

}
