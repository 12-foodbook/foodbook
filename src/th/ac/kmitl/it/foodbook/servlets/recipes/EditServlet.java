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
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;

@WebServlet("/recipes/edit")
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String recipe_id = request.getParameter("id");

		Recipe recipe = null;
		
		DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");

		try {
			Connection conn = ds.getConnection();
			RecipesDAO recipesDAO = new RecipesDAO(conn);

			recipe = recipesDAO.find(Long.parseLong(recipe_id));
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(500);
		}

		request.setAttribute("recipe", recipe);
		request.getRequestDispatcher("/WEB-INF/views/recipes/edit.jsp").include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
