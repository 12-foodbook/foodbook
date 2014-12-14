package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.IngredientCategory;

public class IngredientCategoriesDAO extends AbstractDAO {
    
    public IngredientCategoriesDAO(Connection conn) {
        super(conn);
    }
    
    public List<IngredientCategory> findAll() throws SQLException {
        List<IngredientCategory> ingredientCategories = new ArrayList<IngredientCategory>();
        
        String sql = "SELECT * FROM ingredient_categories";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            IngredientCategory ingredientCategory = new IngredientCategory();
            ingredientCategory.setIngredient_category_id(rs.getLong("ingredient_category_id"));
            ingredientCategory.setName(rs.getString("name"));
            ingredientCategories.add(ingredientCategory);
        }
        
        return ingredientCategories;
    }
    
    public boolean create(IngredientCategory ingredientCategory) throws SQLException {
        String sql = "INSERT INTO ingredient_categories (name) VALUES (?)";
        PreparedStatement stm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        
        stm.setString(1, ingredientCategory.getName());
        
        int rowCount = stm.executeUpdate();
        
        if (rowCount == 1) {
            ResultSet rs = stm.getGeneratedKeys();
            rs.next();
            ingredientCategory.setIngredient_category_id(rs.getLong(1));
            return true;
        }
        
        return false;
    }
    
    public boolean update(IngredientCategory ingredientCategory) throws SQLException {
        String sql = "UPDATE ingredient_categories SET name = ? WHERE ingredient_category_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, ingredientCategory.getName());
        stm.setLong(2, ingredientCategory.getIngredient_category_id());
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean removeAllCategoryFromIngredientCategories(long ingredientCategoryId) throws SQLException {
        String sql = "DELETE FROM ingredients_ingredient_categories WHERE ingredient_category_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, ingredientCategoryId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean delete(long ingredientCategoryId) throws SQLException {
        String sql = "DELETE FROM ingredient_categories WHERE ingredient_category_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, ingredientCategoryId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
}
