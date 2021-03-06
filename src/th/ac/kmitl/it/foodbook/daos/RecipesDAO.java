package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.RecipeCategory;

public class RecipesDAO extends AbstractDAO {
    
    public RecipesDAO(Connection conn) {
        super(conn);
    }
    
    public List<RecipeCategory> getRecipeCategoriesByRecipeId(long id) throws SQLException {
        List<RecipeCategory> recipeCategories = new ArrayList<RecipeCategory>();
        
        String sql = "SELECT * FROM recipes_recipe_categories WHERE recipe_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, id);
        
        ResultSet rs = stm.executeQuery();
        
        RecipeCategoriesDAO recipeCategoriesDAO = new RecipeCategoriesDAO(conn);
        
        while (rs.next()) {
            RecipeCategory recipeCategory = recipeCategoriesDAO.find(rs.getLong("recipe_category_id"));
            recipeCategories.add(recipeCategory);
        }
        
        return recipeCategories;
        
    }
    
    public boolean create(Recipe recipe) throws SQLException {
        String sql = "INSERT INTO recipes (name, description, photo_url, video_url, user_id, is_moderator_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        
        stm.setString(1, recipe.getName());
        stm.setString(2, recipe.getDescription());
        stm.setString(3, recipe.getPhoto_url());
        stm.setString(4, recipe.getVideo_url());
        stm.setLong(5, recipe.getUser_id());
        stm.setBoolean(6, recipe.getIs_moderator_id());
        
        int rowCount = stm.executeUpdate();
        
        if (rowCount == 1) {
            ResultSet rs = stm.getGeneratedKeys();
            rs.next();
            recipe.setRecipe_id(rs.getLong(1));
            return true;
        }
        
        return false;
    }
    
    public Recipe find(long id) throws SQLException {
        Recipe recipe = null;
        
        String sql = "SELECT * FROM recipes WHERE recipe_id = ? LIMIT 1";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, id);
        
        ResultSet rs = stm.executeQuery();
        
        RatesDAO ratesDAO = new RatesDAO(conn);
        
        if (rs.next()) {
            recipe = new Recipe();
            recipe.setRecipe_id(rs.getLong("recipe_id"));
            recipe.setName(rs.getString("name"));
            recipe.setDescription(rs.getString("description"));
            recipe.setPhoto_url(rs.getString("photo_url"));
            recipe.setVideo_url(rs.getString("video_url"));
            recipe.setUser_id(rs.getLong("user_id"));
            recipe.setIs_moderator_id(rs.getBoolean("is_moderator_id"));
            
            double averageRate = ratesDAO.average(rs.getLong("recipe_id"));
            
            recipe.setAverageRate(averageRate);
        }
        
        return recipe;
    }
    
    public List<Recipe> findAll() throws SQLException {
        List<Recipe> recipes = new ArrayList<Recipe>();
        
        String sql = "SELECT * FROM recipes ORDER BY name ASC";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        ResultSet rs = stm.executeQuery();
        
        RatesDAO ratesDAO = new RatesDAO(conn);
        
        while (rs.next()) {
            Recipe recipe = new Recipe();
            recipe.setRecipe_id(rs.getLong("recipe_id"));
            recipe.setName(rs.getString("name"));
            recipe.setDescription(rs.getString("description"));
            recipe.setPhoto_url(rs.getString("photo_url"));
            recipe.setVideo_url(rs.getString("video_url"));
            recipe.setUser_id(rs.getLong("user_id"));
            recipe.setIs_moderator_id(rs.getBoolean("is_moderator_id"));
            
            double averageRate = ratesDAO.average(rs.getLong("recipe_id"));
            
            recipe.setAverageRate(averageRate);
            recipes.add(recipe);
        }
        
        return recipes;
    }
    
    public List<Recipe> findByIngredientId(long ingredientId) throws SQLException {
        List<Recipe> recipes = new ArrayList<Recipe>();
        
        String sql = "SELECT recipe_id FROM recipes_ingredients WHERE ingredient_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, ingredientId);
        
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            long recipeId = rs.getLong("recipe_id");
            Recipe recipe = find(recipeId);
            recipes.add(recipe);
        }
        
        return recipes;
    }
    
    public boolean addIngredient(long recipeId, long ingredientId, float amount) throws SQLException {
        String sql = "INSERT INTO recipes_ingredients (recipe_id, ingredient_id, amount) VALUES (?, ?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeId);
        stm.setLong(2, ingredientId);
        stm.setFloat(3, amount);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean removeIngredient(long recipeId, long ingredientId) throws SQLException {
        String sql = "DELETE FROM recipes_ingredients WHERE recipe_id = ? AND ingredient_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeId);
        stm.setLong(2, ingredientId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean addRecipeCategory(long recipeId, long recipeCategoryId) throws SQLException {
        String sql = "INSERT INTO recipes_recipe_categories (recipe_id, recipe_category_id) VALUES (?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeId);
        stm.setLong(2, recipeCategoryId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean removeRecipeCategory(long recipeId, long recipeCategoryId) throws SQLException {
        String sql = "DELETE FROM recipes_recipe_categories WHERE recipe_id = ? AND recipe_category_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeId);
        stm.setLong(2, recipeCategoryId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean removeAllRecipeCategory(long recipeCategoryId) throws SQLException {
        String sql = "DELETE FROM recipes_recipe_categories WHERE recipe_category_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeCategoryId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean removeAllRecipeFromRecipeCategories(long recipeId) throws SQLException {
        String sql = "DELETE FROM recipes_recipe_categories WHERE recipe_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean removeAllRecipeFromIngredients(long recipeId) throws SQLException {
        String sql = "DELETE FROM recipes_ingredients WHERE recipe_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean removeAllRecipeFromSteps(long recipeId) throws SQLException {
        String sql = "DELETE FROM recipe_steps WHERE recipe_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean delete(long recipeId) throws SQLException {
        FavoritesDAO favoritesDAO = new FavoritesDAO(conn);
        favoritesDAO.removeRecipe(recipeId);
        
        RatesDAO ratesDAO = new RatesDAO(conn);
        ratesDAO.removeByRecipe(recipeId);
        
        CommentsDAO commentsDAO = new CommentsDAO(conn);
        commentsDAO.removeRecipe(recipeId);
        
        removeAllRecipeFromRecipeCategories(recipeId);
        removeAllRecipeFromIngredients(recipeId);
        removeAllRecipeFromSteps(recipeId);
        
        String sql = "DELETE FROM recipes WHERE recipe_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean deleteByUserId(long userId) throws SQLException {
        String sql = "SELECT recipe_id FROM recipes WHERE user_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, userId);
        
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            if (!delete(rs.getLong("recipe_id"))) return false;
        }
        
        return true;
    }
    
    public boolean update(Recipe recipe) throws SQLException {
        String sql = "UPDATE recipes SET name = ?, description = ?, photo_url = ?, video_url = ? WHERE recipe_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, recipe.getName());
        stm.setString(2, recipe.getDescription());
        stm.setString(3, recipe.getPhoto_url());
        stm.setString(4, recipe.getVideo_url());
        stm.setLong(5, recipe.getRecipe_id());
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public List<Recipe> findByNameLike(String query) throws SQLException {
        List<Recipe> recipes = new ArrayList<Recipe>();
        
        String sql = "SELECT * FROM recipes WHERE name LIKE ? ORDER BY name ASC";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, "%" + query + "%");
        
        ResultSet rs = stm.executeQuery();
        
        RatesDAO ratesDAO = new RatesDAO(conn);
        
        while (rs.next()) {
            Recipe recipe = new Recipe();
            recipe.setRecipe_id(rs.getLong("recipe_id"));
            recipe.setName(rs.getString("name"));
            recipe.setDescription(rs.getString("description"));
            recipe.setPhoto_url(rs.getString("photo_url"));
            recipe.setVideo_url(rs.getString("video_url"));
            recipe.setUser_id(rs.getLong("user_id"));
            recipe.setIs_moderator_id(rs.getBoolean("is_moderator_id"));
            
            double averageRate = ratesDAO.average(rs.getLong("recipe_id"));
            
            recipe.setAverageRate(averageRate);
            recipes.add(recipe);
        }
        
        return recipes;
    }
    
    public List<Recipe> findByUserId(long userId) throws SQLException {
        List<Recipe> recipes = new ArrayList<Recipe>();
        
        String sql = "SELECT recipe_id FROM recipes WHERE user_id = ?  ORDER BY name ASC";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, userId);
        
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            long recipeId = rs.getLong("recipe_id");
            Recipe recipe = find(recipeId);
            recipes.add(recipe);
        }
        
        return recipes;
    }
    
    public List<Recipe> findByCategoryId(long categoryId) throws SQLException {
        List<Recipe> recipes = new ArrayList<Recipe>();
        
        String sql = "SELECT recipe_id FROM recipes_recipe_categories WHERE recipe_category_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, categoryId);
        
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            long recipeId = rs.getLong("recipe_id");
            Recipe recipe = find(recipeId);
            recipes.add(recipe);
        }
        
        return recipes;
    }
    
}
