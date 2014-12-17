package th.ac.kmitl.it.foodbook.servlets.ingredients;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.Ingredient;
import th.ac.kmitl.it.foodbook.daos.IngredientsDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/ingredients/edit")
public class EditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public EditServlet() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long ingredientId = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String photoUrl = request.getParameter("photo_url");
        String calorieString = request.getParameter("calorie");
        float calorie = Float.parseFloat(calorieString);
        String unit = request.getParameter("unit");
        String ingredientCategoryIdString = request.getParameter("ingredient_category_id");
        long ingredientCategoryId = Long.parseLong(ingredientCategoryIdString);

        HttpSession session = request.getSession();

        Ingredient ingredient = null;

        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");

        boolean isSuccess = false;

        try {
            Connection conn = ds.getConnection();

            IngredientsDAO ingredientsDAO = new IngredientsDAO(conn);

            ingredient = ingredientsDAO.find(ingredientId);
            ingredient.setName(name);
            ingredient.setPhoto_url(photoUrl);
            ingredient.setCalorie(calorie);
            ingredient.setUnit(unit);
            
            ingredientsDAO.removeAllIngredientFromIngredientCategories(ingredientId);
            ingredientsDAO.addIngredientCategory(ingredientId, ingredientCategoryId);

            if (ingredientsDAO.update(ingredient)) {
                isSuccess = true;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
            return;
        }

        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Updated Successfully :D"));
            response.sendRedirect("/ingredients/index");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Updated Unsuccessfully D:"));
            request.getRequestDispatcher("/WEB-INF/views/ingredients/index.jsp").include(request, response);
        }
    }

}
