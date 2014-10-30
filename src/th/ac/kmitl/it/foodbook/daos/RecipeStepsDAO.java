package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import th.ac.kmitl.it.foodbook.beans.RecipeStep;

public class RecipeStepsDAO extends AbstractDAO {

	public RecipeStepsDAO(Connection conn) {
		super(conn);
	}
	
	public boolean create(RecipeStep recipeStep) throws SQLException {
		String sql = "INSERT INTO recipe_steps (title, description, recipe_id) VALUES (?, ?, ?)";
		PreparedStatement stm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		
		stm.setString(1, recipeStep.getTitle());
		stm.setString(2, recipeStep.getDescription());
		stm.setLong(3, recipeStep.getRecipe_id());
		
		int rowCount = stm.executeUpdate();
		
		if (rowCount == 1) {
			ResultSet rs = stm.getGeneratedKeys();
			rs.next();
			recipeStep.setRecipe_step_id(rs.getLong(1));
			return true;
		}
		
		return false;
	}

}
