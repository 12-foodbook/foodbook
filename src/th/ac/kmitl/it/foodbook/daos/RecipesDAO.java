package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.Recipe;

public class RecipesDAO extends AbstractDAO {
    
    public RecipesDAO(Connection conn) {
        super(conn);
    }
    
    public boolean create(Recipe recipe) throws SQLException {
        String sql = "INSERT INTO recipes (name, video_url, user_id) VALUES (?, ?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        
        stm.setString(1, recipe.getName());
        stm.setString(2, recipe.getVideo_url());
        stm.setLong(3, recipe.getUser_id());
        
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
        
        if (rs.next()) {
            recipe = new Recipe();
            recipe.setRecipe_id(rs.getLong("recipe_id"));
            recipe.setName(rs.getString("name"));
            recipe.setPhoto_url(rs.getString("photo_url"));
            recipe.setVideo_url(rs.getString("video_url"));
            recipe.setUser_id(rs.getLong("user_id"));
        }
        
        return recipe;
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
    
    public boolean addIngredient(long recipeId, long ingredientId, String amount) throws SQLException {
        String sql = "INSERT INTO recipes_ingredients (recipe_id, ingredient_id, amount) VALUES (?, ?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeId);
        stm.setLong(2, ingredientId);
        stm.setString(3, amount);
        
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
    
    public boolean update(Recipe recipe) throws SQLException {
        String sql = "UPDATE recipes SET name = ?, video_url = ? WHERE recipe_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, recipe.getName());
        stm.setString(2, recipe.getVideo_url());
        stm.setLong(3, recipe.getRecipe_id());
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public List<Recipe> findByNameLike(String query) throws SQLException {
        List<Recipe> recipes = new ArrayList<Recipe>();
        
        String sql = "SELECT * FROM recipes WHERE name LIKE ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, "%" + query + "%");
        
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            Recipe recipe = new Recipe();
            recipe.setRecipe_id(rs.getLong("recipe_id"));
            recipe.setName(rs.getString("name"));
            recipe.setPhoto_url(rs.getString("photo_url"));
            recipe.setVideo_url(rs.getString("video_url"));
            recipe.setUser_id(rs.getLong("user_id"));
            recipes.add(recipe);
        }
        
        return recipes;
    }
    
    public List<Recipe> findByUserId(long userId) throws SQLException {
        List<Recipe> recipes = new ArrayList<Recipe>();
        
        String sql = "SELECT recipe_id FROM recipes WHERE user_id = ?";
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
    
}
