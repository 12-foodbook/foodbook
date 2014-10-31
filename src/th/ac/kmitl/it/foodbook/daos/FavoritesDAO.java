package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.Favorite;
import th.ac.kmitl.it.foodbook.beans.Recipe;

public class FavoritesDAO extends AbstractDAO {

	public FavoritesDAO(Connection conn) {
		super(conn);
		
	}
	
	public boolean create(Favorite favorite) throws SQLException {
		String sql = "INSERT INTO favorites (user_id, recipe_id) VALUES (?, ?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setLong(1,favorite.getUser_id());
		stm.setLong(2,favorite.getRecipe_id());
		
		int rowCount = stm.executeUpdate();
		
		if (rowCount == 1) {
			return true;
		}
		
		return false;
	}
	
	public List<Favorite> findByUserId(long userId) throws SQLException {
		List<Favorite> favorites = new ArrayList<Favorite>();
		
		String sql = "SELECT user_id FROM favorites WHERE user_id = ?";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setLong(1, userId);
		
		ResultSet rs = stm.executeQuery();
		
		
		while (rs.next()) {
			long recipeId = rs.getLong("recipe_id");
			Favorite favorite = find(recipeId);
			favorites.add(favorite);
		}
		
		return favorites;
	}
	
	public Favorite find(long id) throws SQLException {
		Favorite favorite = null;

		String sql = "SELECT * FROM favorites WHERE user_id = ? LIMIT 1";
		PreparedStatement stm = conn.prepareStatement(sql);

		stm.setLong(1, id);

		ResultSet rs = stm.executeQuery();

		if (rs.next()) {
			favorite = new Favorite();
			favorite.setRecipe_id(rs.getLong("recipe_id"));
			favorite.setUser_id(rs.getLong("user_id"));
		}

		return favorite;
	}

}
