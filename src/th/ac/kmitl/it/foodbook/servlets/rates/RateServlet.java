package th.ac.kmitl.it.foodbook.servlets.rates;

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

import th.ac.kmitl.it.foodbook.beans.Rate;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.RatesDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/rates")
public class RateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public RateServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String recipeIdString = request.getParameter("recipe_id");
        long recipeId = Long.parseLong(recipeIdString);
        String ratingString = request.getParameter("rate");
        int rating = Integer.parseInt(ratingString);

        Rate rate = null;

        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");

        boolean isSuccess = false;

        try {
            Connection conn = ds.getConnection();
            RatesDAO ratesDAO = new RatesDAO(conn);

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            rate = new Rate();
            rate.setUser_id(user.getUser_id());
            rate.setRecipe_id(recipeId);
            rate.setRate(rating);

            isSuccess = ratesDAO.update(rate);

            if (!isSuccess) isSuccess = ratesDAO.create(rate);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }

        HttpSession session = request.getSession();

        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Rated Successfully :D"));
            response.sendRedirect("/recipes/show?id=" + recipeId);
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Rated Unsuccessfully D:"));
            response.sendRedirect("/recipes/show?id=" + recipeId);
        }
    }
}
