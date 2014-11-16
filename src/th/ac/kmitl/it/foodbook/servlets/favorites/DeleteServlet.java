package th.ac.kmitl.it.foodbook.servlets.favorites;

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

import th.ac.kmitl.it.foodbook.beans.Favorite;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.FavoritesDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/favorites/delete")
public class DeleteServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public DeleteServlet() {
        super();
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String recipeIdString = request.getParameter("recipe_id");
        long recipeId = Long.parseLong(recipeIdString);
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        boolean isSuccess = false;
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        Favorite favorite = new Favorite();
        favorite.setUser_id(user.getUser_id());
        favorite.setRecipe_id(recipeId);
        
        try {
            Connection conn = ds.getConnection();
            
            FavoritesDAO favoritesDAO = new FavoritesDAO(conn);
            isSuccess = favoritesDAO.delete((favorite));
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Deleted Successfully!"));
            response.sendRedirect("/");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Deleted Unsuccessfully!"));
            response.sendRedirect("/favorites/index");
        }
    }
}
