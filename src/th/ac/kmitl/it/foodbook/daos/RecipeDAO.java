package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import th.ac.kmitl.it.foodbook.beans.Recipe;

public class RecipeDAO extends DAO {

	public RecipeDAO(Connection conn) {
		super(conn);
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
			recipe.setVideo_url(rs.getString("video_url"));
			recipe.setUser_id(rs.getLong("user_id"));

		}

		return recipe;
	}

}
