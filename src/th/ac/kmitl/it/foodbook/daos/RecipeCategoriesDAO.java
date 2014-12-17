package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.RecipeCategory;

public class RecipeCategoriesDAO extends AbstractDAO {
    
    public RecipeCategoriesDAO(Connection conn) {
        super(conn);
    }
    
    public boolean create(RecipeCategory recipeCategory) throws SQLException {
        String sql = "INSERT INTO recipe_categories (name) VALUES (?)";
        PreparedStatement stm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        
        stm.setString(1, recipeCategory.getName());
        
        int rowCount = stm.executeUpdate();
        
        if (rowCount == 1) {
            ResultSet rs = stm.getGeneratedKeys();
            rs.next();
            recipeCategory.setRecipe_category_id(rs.getLong(1));
            return true;
        }
        
        return false;
    }
    
    public boolean delete(RecipeCategory recipeCategory) throws SQLException {
        String sql = "DELETE FROM recipe_categories WHERE recipe_category_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeCategory.getRecipe_category_id());
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean removeAllCategoryFromRecipeCategories(long recipeCategoryId) throws SQLException {
        String sql = "DELETE FROM recipes_recipe_categories WHERE recipe_category_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeCategoryId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean update(RecipeCategory recipeCategory) throws SQLException {
        String sql = "UPDATE recipe_categories SET name = ? WHERE recipe_category_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, recipeCategory.getName());
        stm.setLong(2, recipeCategory.getRecipe_category_id());
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public RecipeCategory find(long id) throws SQLException {
        RecipeCategory recipeCategory = null;
        
        String sql = "SELECT * FROM recipe_categories WHERE recipe_category_id = ? LIMIT 1";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, id);
        
        ResultSet rs = stm.executeQuery();
        
        if (rs.next()) {
            recipeCategory = new RecipeCategory();
            recipeCategory.setRecipe_category_id(rs.getLong("recipe_category_id"));
            recipeCategory.setName(rs.getString("name"));
        }
        
        return recipeCategory;
    }
    
    public List<RecipeCategory> findAll() throws SQLException {
        String sql = "SELECT * FROM recipe_categories ORDER BY name ASC";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        ResultSet rs = stm.executeQuery();
        
        List<RecipeCategory> recipeCategories = new ArrayList<RecipeCategory>();
        
        while (rs.next()) {
            RecipeCategory recipeCategory = new RecipeCategory();
            recipeCategory.setRecipe_category_id(rs.getLong("recipe_category_id"));
            recipeCategory.setName(rs.getString("name"));
            recipeCategories.add(recipeCategory);
        }
        
        return recipeCategories;
    }
    
    public List<RecipeCategory> findByRecipeId(long recipeId) throws SQLException {
        String sql = "SELECT * FROM recipes_recipe_categories where recipe_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeId);
        
        ResultSet rs = stm.executeQuery();
        
        List<RecipeCategory> recipeCategories = new ArrayList<RecipeCategory>();
        
        while (rs.next()) {
            RecipeCategory recipeCategory = new RecipeCategory();
            recipeCategory = find(rs.getLong("recipe_category_id"));
            recipeCategories.add(recipeCategory);
        }
        
        return recipeCategories;
    }
    
}
