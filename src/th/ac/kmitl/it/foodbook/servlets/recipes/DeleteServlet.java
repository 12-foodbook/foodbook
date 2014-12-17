package th.ac.kmitl.it.foodbook.servlets.recipes;

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

import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/recipes/delete")
public class DeleteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public DeleteServlet() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String recipeIdString = request.getParameter("recipe_id");
        long recipeId = Long.parseLong(recipeIdString);

        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");

        HttpSession session = request.getSession();

        boolean isSuccess = false;

        try {
            Connection conn = ds.getConnection();

            RecipesDAO recipesDAO = new RecipesDAO(conn);
            Recipe recipe = recipesDAO.find(recipeId);

            User user = (User) session.getAttribute("user");

            if (recipe.getUser_id() != user.getUser_id()) {
                session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Can not be accessed D:"));
                response.sendRedirect("/");
                return;
            }

            isSuccess = recipesDAO.delete(recipeId);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }

        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Deleted recipe successfully :D"));
            response.sendRedirect("/recipes/user");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Deleted recipe unsuccessfully :D"));
            response.sendRedirect("/recipes/index");
        }
    }
}
