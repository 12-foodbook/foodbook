package th.ac.kmitl.it.foodbook.servlets.favorites;

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

import th.ac.kmitl.it.foodbook.beans.Favorite;
import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.FavoritesDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/favorites/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public IndexServlet() {
    	super();
    }

    protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
    	DataSource ds = (DataSource) request.getServletContext().getAttribute(
				"ds");
		
		HttpSession session = request.getSession();
		
		boolean isSuccess = false;
		
		List<Favorite> favorites = null;
		List<Recipe> recipes = new ArrayList<Recipe>();
		
		try{
			Connection conn = ds.getConnection();
			FavoritesDAO favoritesDAO = new FavoritesDAO(conn);
			User user = (User) session.getAttribute("user");
			favorites = favoritesDAO.findByUserId(user.getUser_id());
			for(Favorite i:favorites){
				RecipesDAO recipesDAO = new RecipesDAO(conn);
				Recipe recipe = recipesDAO.find(i.getRecipe_id());
				recipes.add(recipe);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(500);
		}
		System.out.print(recipes.size());
		
		request.setAttribute("recipes", recipes);
		request.getRequestDispatcher("/WEB-INF/views/recipes/index.jsp").include(request, response);
		

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		


		

		

		
	}
}
