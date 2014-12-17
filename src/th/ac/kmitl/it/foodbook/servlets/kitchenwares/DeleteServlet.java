package th.ac.kmitl.it.foodbook.servlets.kitchenwares;

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

import th.ac.kmitl.it.foodbook.daos.KitchenwaresDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/kitchenwares/delete")
public class DeleteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public DeleteServlet() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] kitchenwareIdsString = request.getParameterValues("kitchenware_id");

        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");

        boolean isSuccess = false;

        HttpSession session = request.getSession();

        try {
            Connection conn = ds.getConnection();

            if (kitchenwareIdsString == null) {
                response.sendRedirect("/kitchenwares/index");
                return;
            }

            for (String kitchenwareIdString : kitchenwareIdsString) {
                long kitchenwareId = Long.parseLong(kitchenwareIdString);

                KitchenwaresDAO kitchenwaresDAO = new KitchenwaresDAO(conn);
                kitchenwaresDAO.removeAllKitchenwareFromRecipes(kitchenwareId);
                isSuccess = kitchenwaresDAO.delete((kitchenwareId));

            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }

        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Deleted Successfully :D"));
            response.sendRedirect("/kitchenwares/index");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Deleted Unsuccessfully D:"));
            response.sendRedirect("/kitchenwares/index");
        }
    }
}
