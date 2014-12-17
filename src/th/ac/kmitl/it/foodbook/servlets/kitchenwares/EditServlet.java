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

@WebServlet("/kitchenwares/edit")
public class EditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public EditServlet() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long kitchenwareId = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");

        HttpSession session = request.getSession();

        Kitchenware kitchenware = null;

        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");

        boolean isSuccess = false;

        try {
            Connection conn = ds.getConnection();

            KitchenwaresDAO kitchenwaresDAO = new KitchenwaresDAO(conn);

            kitchenware = kitchenwaresDAO.find(kitchenwareId);
            kitchenware.setName(name);

            if (kitchenwaresDAO.update(kitchenware)) {
                isSuccess = true;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
            return;
        }

        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "แก้ไขเครื่องครัวสำเร็จ :D"));
            response.sendRedirect("/kitchenwares/index");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "แก้ไขเครื่องครัวไม่สำเร็จ D:"));
            request.getRequestDispatcher("/WEB-INF/views/kitchenwares/index.jsp").include(request, response);
        }
    }

}
