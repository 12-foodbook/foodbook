package th.ac.kmitl.it.foodbook.servlets.recipes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.Kitchenware;
import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.RecipeCategory;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.KitchenwaresDAO;
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
        List<List<RecipeCategory>> recipesCategories = null;
        List<Kitchenware> kitchenwares = null;
        List<List<Kitchenware>> recipesKitchenwares = null;
        
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        
        HttpSession session = request.getSession();
        
        try {
            Connection conn = ds.getConnection();
            
            if (session.getAttribute("user") != null && userIdString == null) {
                user = (User) session.getAttribute("user");
            } else if (userIdString != null) {
                long userId = Long.parseLong(userIdString);
                
                UsersDAO usersDAO = new UsersDAO(conn);
                user = usersDAO.find(userId);
            } else {
                response.sendRedirect("/");
                return;
            }
            
            RecipesDAO recipesDAO = new RecipesDAO(conn);
            recipes = recipesDAO.findByUserId(user.getUser_id());
            
            Collections.sort(recipes, new Comparator<Recipe>() {
                @Override
                public int compare(Recipe o1, Recipe o2) {
                    return o1.getAverageRate() < o2.getAverageRate() ? 1 : -1;
                }
            });

            RecipeCategoriesDAO recipeCategoriesDAO = new RecipeCategoriesDAO(conn);
            recipesCategories = new ArrayList<List<RecipeCategory>>();
            
            KitchenwaresDAO kitchenwaresDAO = new KitchenwaresDAO(conn);
            recipesKitchenwares = new ArrayList<List<Kitchenware>>();
            
            for (Recipe recipe : recipes) {
                List<RecipeCategory> aRecipesCategories = recipeCategoriesDAO.findByRecipeId(recipe.getRecipe_id());
                recipesCategories.add(aRecipesCategories);
                
                List<Kitchenware> aRecipesKitchenwares = kitchenwaresDAO.findByRecipeId(recipe.getRecipe_id());
                recipesKitchenwares.add(aRecipesKitchenwares);
            }
            
            recipeCategories = recipeCategoriesDAO.findAll();
            kitchenwares = kitchenwaresDAO.findAll();
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
        
        request.setAttribute("kitchenwares", kitchenwares);
        request.setAttribute("recipesUser", user);
        request.setAttribute("recipes", recipes);
        request.setAttribute("recipeCategories", recipeCategories);
        request.setAttribute("recipesCategories", recipesCategories);
        request.setAttribute("recipesKitchenwares", recipesKitchenwares);
        
        request.getRequestDispatcher("/WEB-INF/views/recipes/user.jsp").include(request, response);
    }
    
}
