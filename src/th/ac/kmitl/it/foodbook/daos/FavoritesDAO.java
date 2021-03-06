package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.Favorite;

public class FavoritesDAO extends AbstractDAO {
    
    public FavoritesDAO(Connection conn) {
        super(conn);
    }
    
    public boolean create(Favorite favorite) throws SQLException {
        String sql = "INSERT INTO favorites (user_id, recipe_id) VALUES (?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        if(find(favorite.getUser_id(), favorite.getRecipe_id()) != null){
            return false;
        }
        
        stm.setLong(1, favorite.getUser_id());
        stm.setLong(2, favorite.getRecipe_id());
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean delete(Favorite favorite) throws SQLException {
        String sql = "DELETE FROM favorites WHERE user_id = ? AND recipe_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, favorite.getUser_id());
        stm.setLong(2, favorite.getRecipe_id());
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public boolean deleteByUserId(long userId) throws SQLException {
        String sql = "DELETE FROM favorites WHERE user_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, userId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount > 0;
    }
    
    public boolean removeRecipe(long recipeId) throws SQLException {
        String sql = "DELETE FROM favorites WHERE recipe_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public List<Favorite> findByUserId(long userId) throws SQLException {
        List<Favorite> favorites = new ArrayList<Favorite>();
        Favorite favorite = null;
        
        String sql = "SELECT * FROM favorites WHERE user_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, userId);
        
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            favorite = new Favorite();
            favorite.setRecipe_id(rs.getLong("recipe_id"));
            favorite.setUser_id(rs.getLong("user_id"));
            favorites.add(favorite);
        }
        
        return favorites;
    }
    
    public Favorite find(long userId, long recipeId) throws SQLException {
        List<Favorite> favorites = findByUserId(userId);
        
        for (Favorite favorite : favorites) {
            if (favorite.getRecipe_id() == recipeId) return favorite;
        }
        
        return null;
    }
    
}
