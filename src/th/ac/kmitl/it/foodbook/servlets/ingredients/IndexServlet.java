package th.ac.kmitl.it.foodbook.servlets.ingredients;

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
import th.ac.kmitl.it.foodbook.beans.Kitchenware;
import th.ac.kmitl.it.foodbook.daos.IngredientCategoriesDAO;
import th.ac.kmitl.it.foodbook.daos.IngredientsDAO;
import th.ac.kmitl.it.foodbook.daos.KitchenwaresDAO;

@WebServlet("/ingredients/index")
public class IndexServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public IndexServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Ingredient> ingredients = null;
        List<IngredientCategory> ingredientCategories = null;
        List<List<IngredientCategory>> ingredientsCategories = null;
        List<Kitchenware> kitchenwares = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        try {
            Connection conn = ds.getConnection();
            
            IngredientsDAO ingredientsDAO = new IngredientsDAO(conn);
            ingredients = ingredientsDAO.findAll();
            
            IngredientCategoriesDAO ingredientCategoriesDAO = new IngredientCategoriesDAO(conn);
            ingredientCategories = ingredientCategoriesDAO.findAll();
            
            ingredientsCategories = new ArrayList<List<IngredientCategory>>();
            
            for (Ingredient ingredient : ingredients) {
                List<IngredientCategory> aIngredientCategories = ingredientsDAO.getIngredientCategoriesByIngredientId(ingredient.getIngredient_id());
                ingredientsCategories.add(aIngredientCategories);
            }
            
            KitchenwaresDAO kitchenwaresDAO = new KitchenwaresDAO(conn);
            kitchenwares = kitchenwaresDAO.findAll();
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        request.setAttribute("ingredients", ingredients);
        request.setAttribute("ingredientCategories", ingredientCategories);
        request.setAttribute("kitchenwares", kitchenwares);
        request.setAttribute("ingredientsCategories", ingredientsCategories);
        request.getRequestDispatcher("/WEB-INF/views/ingredients/index.jsp").include(request, response);
    }
    
}
