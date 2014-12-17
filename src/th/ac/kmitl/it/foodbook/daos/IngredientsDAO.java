package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.Ingredient;
import th.ac.kmitl.it.foodbook.beans.IngredientCategory;

public class IngredientsDAO extends AbstractDAO {
    
    public IngredientsDAO(Connection conn) {
        super(conn);
    }
    
    public List<IngredientCategory> getIngredientCategoriesByIngredientId(long id) throws SQLException {
        List<IngredientCategory> ingredientCategories = new ArrayList<IngredientCategory>();
        
        String sql = "SELECT * FROM ingredients_ingredient_categories WHERE ingredient_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, id);
        
        ResultSet rs = stm.executeQuery();
        
        IngredientCategoriesDAO ingredientCategoriesDAO = new IngredientCategoriesDAO(conn);
        
        while (rs.next()) {
            IngredientCategory ingredientCategory = ingredientCategoriesDAO.find(rs.getLong("ingredient_category_id"));
            ingredientCategories.add(ingredientCategory);
        }
        
        return ingredientCategories;
        
    }
    
    public Ingredient find(long id) throws SQLException {
        Ingredient ingredient = null;
        
        String sql = "SELECT * FROM ingredients WHERE ingredient_id = ? LIMIT 1";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, id);
        
        ResultSet rs = stm.executeQuery();
        
        if (rs.next()) {
            ingredient = new Ingredient();
            ingredient.setIngredient_id(rs.getLong("ingredient_id"));
            ingredient.setName(rs.getString("name"));
            ingredient.setPhoto_url(rs.getString("photo_url"));
            ingredient.setCalorie(rs.getFloat("calorie"));
            ingredient.setUnit(rs.getString("unit"));
            ingredient.setIngredient_categories(getIngredientCategoriesByIngredientId(rs.getLong("ingredient_id")));
            
        }
        
        return ingredient;
    }
    
    public List<Ingredient> findByRecipeId(long recipeId) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        
        String sql = "SELECT ingredient_id, amount FROM recipes_ingredients WHERE recipe_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeId);
        
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            long ingredientId = rs.getLong("ingredient_id");
            Ingredient ingredient = find(ingredientId);
            ingredient.setAmount(rs.getFloat("amount"));
            ingredients.add(ingredient);
        }
        
        return ingredients;
    }
    
    public List<Ingredient> findByIngredientCategoryId(long ingredientCategoryId) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        
        String sql = "SELECT ingredient_id FROM ingredients_ingredient_categories WHERE ingredient_category_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, ingredientCategoryId);
        
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            long ingredientId = rs.getLong("ingredient_id");
            Ingredient ingredient = find(ingredientId);
            ingredients.add(ingredient);
        }
        
        return ingredients;
    }
    
    public List<Ingredient> findAll() throws SQLException {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        
        String sql = "SELECT * FROM ingredients ORDER BY name ASC";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            Ingredient ingredient = find(rs.getLong("ingredient_id"));
            ingredients.add(ingredient);
        }
        
        return ingredients;
    }
    
    public boolean create(Ingredient ingredient) throws SQLException {
        String sql = "INSERT INTO ingredients (name, photo_url, calorie, unit) VALUES (?, ?, ?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        
        stm.setString(1, ingredient.getName());
        stm.setString(2, ingredient.getPhoto_url());
        stm.setFloat(3, ingredient.getCalorie());
        stm.setString(4, ingredient.getUnit());
        
        int rowCount = stm.executeUpdate();
        
        if (rowCount == 1) {
            ResultSet rs = stm.getGeneratedKeys();
            rs.next();
            ingredient.setIngredient_id(rs.getLong(1));
            return true;
        }
        
        return false;
    }
    
    public boolean update(Ingredient ingredient) throws SQLException {
        String sql = "UPDATE ingredients SET name = ?, photo_url = ?, calorie = ?, unit = ? WHERE ingredient_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, ingredient.getName());
        stm.setString(2, ingredient.getPhoto_url());
        stm.setFloat(3, ingredient.getCalorie());
        stm.setString(4, ingredient.getUnit());
        stm.setLong(5, ingredient.getIngredient_id());
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean addIngredientCategory(long ingredientId, long ingredientCategoryId) throws SQLException {
        String sql = "INSERT INTO ingredients_ingredient_categories (ingredient_id, ingredient_category_id) VALUES (?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, ingredientId);
        stm.setLong(2, ingredientCategoryId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean removeAllIngredientFromIngredientCategories(long ingredientId) throws SQLException {
        String sql = "DELETE FROM ingredients_ingredient_categories WHERE ingredient_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, ingredientId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean removeAllIngredientFromRecipes(long ingredientId) throws SQLException {
        String sql = "DELETE FROM recipes_ingredients WHERE ingredient_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, ingredientId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean delete(long ingredientId) throws SQLException {
        String sql = "DELETE FROM ingredients WHERE ingredient_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, ingredientId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
}
