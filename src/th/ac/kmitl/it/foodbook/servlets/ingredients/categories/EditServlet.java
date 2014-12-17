package th.ac.kmitl.it.foodbook.servlets.ingredients.categories;

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

import th.ac.kmitl.it.foodbook.beans.IngredientCategory;
import th.ac.kmitl.it.foodbook.daos.IngredientCategoriesDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/ingredients/categories/edit")
public class EditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public EditServlet() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long ingredientCategoryId = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");

        HttpSession session = request.getSession();

        IngredientCategory ingredientCategory = null;

        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");

        boolean isSuccess = false;

        try {
            Connection conn = ds.getConnection();

            IngredientCategoriesDAO ingredientCategoriesDAO = new IngredientCategoriesDAO(conn);

            ingredientCategory = ingredientCategoriesDAO.find(ingredientCategoryId);
            ingredientCategory.setName(name);

            if (ingredientCategoriesDAO.update(ingredientCategory)) {
                isSuccess = true;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
            return;
        }

        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "แก้ไขวัตถุดิบสำเร็จ :D"));
            response.sendRedirect("/ingredient/categories/index");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "แก้ไขวัตถุดิบไม่สำเร็จ D:"));
            request.getRequestDispatcher("/WEB-INF/views/ingredients/categories/index.jsp").include(request, response);
        }
    }

}
