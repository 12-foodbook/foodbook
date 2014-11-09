package th.ac.kmitl.it.foodbook.servlets;

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
import th.ac.kmitl.it.foodbook.beans.IngredientCategory;
import th.ac.kmitl.it.foodbook.daos.IngredientCategoriesDAO;
import th.ac.kmitl.it.foodbook.daos.IngredientsDAO;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public IndexServlet() {
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<IngredientCategory> ingredientCategories = null;
    	List<List<Ingredient>> ingredients = null;
    	
    	DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
    	
    	try {
    		Connection conn = ds.getConnection();
	    	IngredientCategoriesDAO ingredientCategoriesDAO = new IngredientCategoriesDAO(conn);
	    	
	    	ingredientCategories = ingredientCategoriesDAO.findAll();
	    	
	    	IngredientsDAO ingredientsDAO = new IngredientsDAO(conn);
	    	
	    	ingredients = new ArrayList<List<Ingredient>>();
	    	
	    	for (IngredientCategory ingredientCategory : ingredientCategories) {
	    		List<Ingredient> tempIngredients = ingredientsDAO.findByIngredientCategoryId(ingredientCategory.getIngredient_category_id());
	    		ingredients.add(tempIngredients);
	    	}
	    	
	    	conn.close();
    	} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(500);
		}
    	
    	request.setAttribute("ingredientCategories", ingredientCategories);
    	request.setAttribute("ingredients", ingredients);
    	
    	request.getRequestDispatcher("/WEB-INF/views/index.jsp").include(request, response);
    }

}
