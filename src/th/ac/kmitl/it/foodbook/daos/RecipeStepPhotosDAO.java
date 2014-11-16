package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import th.ac.kmitl.it.foodbook.beans.RecipeStepPhoto;

public class RecipeStepPhotosDAO extends AbstractDAO {
    
    public RecipeStepPhotosDAO(Connection conn) {
        super(conn);
    }
    
    public boolean create(RecipeStepPhoto recipeStepPhoto) throws SQLException {
        String sql = "INSERT INTO recipe_step_photos (photo_url, recipe_step_id) VALUES (?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        
        stm.setString(1, recipeStepPhoto.getPhoto_url());
        stm.setLong(2, recipeStepPhoto.getRecipe_step_id());
        
        int rowCount = stm.executeUpdate();
        
        if (rowCount == 1) {
            ResultSet rs = stm.getGeneratedKeys();
            rs.next();
            recipeStepPhoto.setRecipe_step_photo_id(rs.getLong(1));
            return true;
        }
        
        return false;
    }
    
    public boolean deleteByRecipeStepId(long recipeStepId) throws SQLException {
        String sql = "DELETE FROM recipe_step_photos WHERE recipe_step_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeStepId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount > 0;
    }
    
}
