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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.Favorite;
import th.ac.kmitl.it.foodbook.beans.Ingredient;
import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.FavoritesDAO;
import th.ac.kmitl.it.foodbook.daos.IngredientsDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TestServlet() {
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/plain");
    	PrintWriter out = response.getWriter();
    	String recipeId = request.getParameter("recipe_id");

		Favorite favorite = null;

		DataSource ds = (DataSource) request.getServletContext().getAttribute(
				"ds");

		boolean isSuccess = false;

		try {
			Connection conn = ds.getConnection();
			FavoritesDAO favoritesDAO = new FavoritesDAO(conn);

			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			favorite = new Favorite();

			favorite.setUser_id(user.getUser_id());
			favorite.setRecipe_id(Long.parseLong(recipeId));

			isSuccess = favoritesDAO.delete((favorite));
			
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(500);
		}

		HttpSession session = request.getSession();

		if (isSuccess) {
			session.setAttribute("alert", new Alert(AlertTypes.SUCCESS,
					"Deleted Successfully!"));
			response.sendRedirect("/");
		} else {
			session.setAttribute("alert", new Alert(AlertTypes.DANGER,
					"Deleted Unsuccessfully!"));
			response.sendRedirect("/users/favorites");
		}
	}
}
