package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
