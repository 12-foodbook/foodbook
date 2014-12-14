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

import th.ac.kmitl.it.foodbook.daos.IngredientsDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/ingredients/delete")
public class DeleteServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public DeleteServlet() {
        super();
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] ingredientIdsString = request.getParameterValues("ingredient_id");
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        boolean isSuccess = false;
        
        HttpSession session = request.getSession();
        
        try {
            Connection conn = ds.getConnection();
            
            if (ingredientIdsString == null) {
                response.sendRedirect("/ingredients/index");
                return;
            }
            
            for (String ingredientIdString : ingredientIdsString) {
                long ingredientId = Long.parseLong(ingredientIdString);
                
                IngredientsDAO ingredientsDAO = new IngredientsDAO(conn);
                ingredientsDAO.removeAllIngredientFromIngredientCategories(ingredientId);
                ingredientsDAO.removeAllIngredientFromRecipes(ingredientId);
                isSuccess = ingredientsDAO.delete((ingredientId));
                
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "Deleted Successfully!"));
            response.sendRedirect("/ingredients/index");
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "Deleted Unsuccessfully!"));
            response.sendRedirect("/ingredients/index");
        }
    }
}
