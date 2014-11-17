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
    
    public boolean update(RecipeCategory recipeCategory) throws SQLException {
        String sql = "UPDATE recipe_categories SET name = ? WHERE recipe_category_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, recipeCategory.getName());
        stm.setLong(2, recipeCategory.getRecipe_category_id());
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public List<RecipeCategory> findAll() throws SQLException {
        String sql = "SELECT * FROM recipe_categories";
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
    
}
