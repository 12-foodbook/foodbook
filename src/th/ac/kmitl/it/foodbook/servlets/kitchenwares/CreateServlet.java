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

import th.ac.kmitl.it.foodbook.beans.Kitchenware;
import th.ac.kmitl.it.foodbook.daos.KitchenwaresDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/kitchenwares/create")
public class CreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public CreateServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/kitchenwares/create.jsp").include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String photoUrl = request.getParameter("photo_url");

        Kitchenware kitchenware = null;

        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");

        boolean isSuccess = false;

        try {
            Connection conn = ds.getConnection();

            KitchenwaresDAO kitchenwaresDAO = new KitchenwaresDAO(conn);

            kitchenware = new Kitchenware();
            kitchenware.setName(name);
            kitchenware.setPhoto_url(photoUrl);

            isSuccess = kitchenwaresDAO.create(kitchenware);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }

        HttpSession session = request.getSession();

        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Created kitchenwares successfully :D"));
            response.sendRedirect("/kitchenwares/index");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Created kitchenwares unsuccessfully D:"));
            request.getRequestDispatcher("/WEB-INF/views/kitchenwares/create.jsp").include(request, response);
        }
    }

}
