package th.ac.kmitl.it.foodbook.servlets.recipes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;
import th.ac.kmitl.it.foodbook.daos.UsersDAO;

/**
 * Servlet implementation class ShowServlet
 */
@WebServlet("/recipes/show")
public class ShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ShowServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String recipe_id = request.getParameter("id");
		DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
		
		Recipe recipe = null;
		
		try {
			Connection conn = ds.getConnection();
			RecipesDAO recipesDAO = new RecipesDAO(conn);
			
			recipe = recipesDAO.find(Long.parseLong(recipe_id));
		} catch (SQLException e) {
			e.printStackTrace();
			
			response.sendError(500);
		}
		System.out.println(recipe.getName());
		
		request.setAttribute("recipe", recipe);
		
		request.getRequestDispatcher("/WEB-INF/views/recipes/show.jsp").include(request, response);
	}

}
