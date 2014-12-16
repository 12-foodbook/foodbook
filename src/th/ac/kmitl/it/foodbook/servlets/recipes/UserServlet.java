package th.ac.kmitl.it.foodbook.servlets.recipes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.RecipeCategory;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.RecipeCategoriesDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;
import th.ac.kmitl.it.foodbook.daos.UsersDAO;

@WebServlet("/recipes/user")
public class UserServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public UserServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdString = request.getParameter("id");
        User user = null;
        List<Recipe> recipes = null;
        List<RecipeCategory> recipeCategories = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        HttpSession session = request.getSession();
        
        try {
            Connection conn = ds.getConnection();
            
            if (session.getAttribute("user") != null) {
                user = (User) session.getAttribute("user");
            } else if (userIdString != null) {
                long userId = Long.parseLong(userIdString);
                
                UsersDAO usersDAO = new UsersDAO(conn);
                user = usersDAO.find(userId);
            } else {
                response.sendRedirect("/");
                return;
            }
            
            request.setAttribute("user", user);
            
            RecipesDAO recipesDAO = new RecipesDAO(conn);
            recipes = recipesDAO.findByUserId(user.getUser_id());
            
            RecipeCategoriesDAO recipeCategoriesDAO = new RecipeCategoriesDAO(conn);
            recipeCategories = recipeCategoriesDAO.findAll();
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        request.setAttribute("recipes", recipes);
        request.setAttribute("recipeCategories", recipeCategories);
        
        request.getRequestDispatcher("/WEB-INF/views/recipes/user.jsp").include(request, response);
    }
    
}
