package th.ac.kmitl.it.foodbook.servlets.recipes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.Ingredient;
import th.ac.kmitl.it.foodbook.beans.IngredientCategory;
import th.ac.kmitl.it.foodbook.beans.Moderator;
import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.RecipeCategory;
import th.ac.kmitl.it.foodbook.beans.RecipeStep;
import th.ac.kmitl.it.foodbook.beans.RecipeStepPhoto;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.IngredientCategoriesDAO;
import th.ac.kmitl.it.foodbook.daos.IngredientsDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeCategoriesDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeStepPhotosDAO;
import th.ac.kmitl.it.foodbook.daos.RecipeStepsDAO;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;
import th.ac.kmitl.it.foodbook.utils.Alert;
import th.ac.kmitl.it.foodbook.utils.Alert.AlertTypes;

@WebServlet("/recipes/create")
public class CreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public CreateServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RecipeCategory> recipeCategories = null;
        List<IngredientCategory> ingredientCategories = null;
        List<List<Ingredient>> ingredients = null;

        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");

        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null && session.getAttribute("moderator") == null) {
            response.sendRedirect("/");
            return;
        }

        try {
            Connection conn = ds.getConnection();

            RecipeCategoriesDAO recipeCategoriesDAO = new RecipeCategoriesDAO(conn);
            recipeCategories = recipeCategoriesDAO.findAll();

            IngredientCategoriesDAO ingredientCategoriesDAO = new IngredientCategoriesDAO(conn);
            ingredientCategories = ingredientCategoriesDAO.findAll();

            ingredients = new ArrayList<List<Ingredient>>();

            IngredientsDAO ingredientsDAO = new IngredientsDAO(conn);

            for (IngredientCategory ingredientCategory : ingredientCategories) {
                List<Ingredient> tempIngredients = ingredientsDAO.findByIngredientCategoryId(ingredientCategory.getIngredient_category_id());
                ingredients.add(tempIngredients);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }

        request.setAttribute("recipeCategories", recipeCategories);
        request.setAttribute("ingredientCategories", ingredientCategories);
        request.setAttribute("ingredients", ingredients);

        request.getRequestDispatcher("/WEB-INF/views/recipes/create.jsp").include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String photoUrl = request.getParameter("photo_url");
        String videoUrl = request.getParameter("video_url");

        String[] recipeCategoryIds = request.getParameterValues("recipe_category_id");

        String[] ingredientIds = request.getParameterValues("ingredient_id");
        String[] ingredientAmounts = request.getParameterValues("ingredient_amount");

        String[] stepTitles = request.getParameterValues("step_title");
        String[] stepDescriptions = request.getParameterValues("step_description");
        String[] stepPhotoUrls = request.getParameterValues("step_photo_url");

        HttpSession session = request.getSession();

        if (name.equals("") || ingredientIds.length == 0 || ingredientAmounts.length == 0 || stepTitles.length == 0) {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "ข้อมูลนำเข้าไม่ถูกต้อง D:"));
            request.getRequestDispatcher("/WEB-INF/views/recipes/create.jsp").include(request, response);
            return;
        }

        Recipe recipe = null;

        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");

        boolean isSuccess = false;

        try {
            Connection conn = ds.getConnection();

            RecipesDAO recipesDAO = new RecipesDAO(conn);

            recipe = new Recipe();
            recipe.setName(name);
            recipe.setDescription(description);
            recipe.setPhoto_url(photoUrl);
            recipe.setVideo_url(videoUrl);

            if (session.getAttribute("moderator") != null) {
                Moderator moderator = (Moderator) session.getAttribute("moderator");
                recipe.setUser_id(moderator.getModerator_id());
                recipe.setIs_moderator_id(true);
            } else {
                User user = (User) session.getAttribute("user");
                recipe.setUser_id(user.getUser_id());
                recipe.setIs_moderator_id(false);
            }

            if (recipesDAO.create(recipe)) {
                isSuccess = true;

                for (String recipeCategoryIdString : recipeCategoryIds) {
                    long recipeCategoryId = Long.parseLong(recipeCategoryIdString);
                    recipesDAO.addRecipeCategory(recipe.getRecipe_id(), recipeCategoryId);
                }

                for (int i = 0; i < ingredientIds.length; i++) {
                    long ingredientId = Integer.parseInt(ingredientIds[i]);

                    if (!recipesDAO.addIngredient(recipe.getRecipe_id(), ingredientId, ingredientAmounts[i])) {
                        isSuccess = false;
                        return;
                    }
                }

                RecipeStepsDAO recipeStepsDAO = new RecipeStepsDAO(conn);
                RecipeStepPhotosDAO recipeStepPhotosDAO = new RecipeStepPhotosDAO(conn);

                for (int i = 0; i < stepTitles.length; i++) {
                    RecipeStep recipeStep = new RecipeStep();
                    recipeStep.setTitle(stepTitles[i]);
                    recipeStep.setDescription(stepDescriptions[i]);
                    recipeStep.setRecipe_id(recipe.getRecipe_id());

                    if (recipeStepsDAO.create(recipeStep)) {
                        RecipeStepPhoto recipeStepPhoto = new RecipeStepPhoto();
                        recipeStepPhoto.setPhoto_url(stepPhotoUrls[i]);
                        recipeStepPhoto.setRecipe_step_id(recipeStep.getRecipe_step_id());
                        recipeStepPhotosDAO.create(recipeStepPhoto);
                    } else {
                        isSuccess = false;
                        return;
                    }
                }
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }

        if (isSuccess) {
            session.setAttribute("alert", new Alert(AlertTypes.SUCCESS, "สร้างตำรับออาหารสำเร็จ :D"));
            response.sendRedirect("/recipes/show?id=" + recipe.getRecipe_id());
        } else {
            session.setAttribute("alert", new Alert(AlertTypes.DANGER, "สร้างตำรับออาหารไม่สำเร็จ D:"));
            request.getRequestDispatcher("/WEB-INF/views/recipes/create.jsp").include(request, response);
        }
    }

}
