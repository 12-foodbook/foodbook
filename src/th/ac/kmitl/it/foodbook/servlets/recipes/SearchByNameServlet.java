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

import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.RecipeCategory;
import th.ac.kmitl.it.foodbook.daos.RecipeCategoriesDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;

@WebServlet("/recipes/search-by-name")
public class SearchByNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public SearchByNameServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("query");
		
		List<Recipe> recipes = null;
		
		List<RecipeCategory> recipeCategories = null;
		
		DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
		
		try {
			Connection conn = ds.getConnection();
			
			RecipesDAO recipesDAO = new RecipesDAO(conn);
			
			RecipeCategoriesDAO recipeCategoriesDAO = new RecipeCategoriesDAO(conn);
			
			recipeCategories = recipeCategoriesDAO.findAll();
			
			recipes = recipesDAO.findByNameLike(query);
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(500);
		}
		
		request.setAttribute("recipes", recipes);
		request.setAttribute("recipeCategories", recipeCategories);
		request.getRequestDispatcher("/WEB-INF/views/recipes/index.jsp").include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}