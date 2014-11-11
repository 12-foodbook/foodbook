package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		
		System.out.println("RecipeStepsDAO#create: " + recipeStep);
		
		int rowCount = stm.executeUpdate();
		
		if (rowCount == 1) {
			ResultSet rs = stm.getGeneratedKeys();
			rs.next();
			recipeStep.setRecipe_step_id(rs.getLong(1));
			return true;
		}
		
		return false;
	}

	public List<RecipeStep> findByRecipeId(long recipeId) throws SQLException {
		List<RecipeStep> recipeSteps = new ArrayList<RecipeStep>();
		
		String sql = "SELECT * FROM recipe_steps WHERE recipe_id = ?";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setLong(1, recipeId);
		
		ResultSet rs = stm.executeQuery();
		
		while (rs.next()) {
			RecipeStep recipeStep = new RecipeStep();
			recipeStep.setRecipe_step_id(rs.getLong("recipe_step_id"));
			recipeStep.setTitle(rs.getString("title"));
			recipeStep.setDescription(rs.getString("description"));
			recipeStep.setRecipe_id(rs.getLong("recipe_id"));
			recipeSteps.add(recipeStep);
		}
		
		return recipeSteps;
	}
	
	public boolean deleteByRecipeId(long recipeId) throws SQLException {
		String sql = "DELETE FROM recipe_steps WHERE recipe_id = ?";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setLong(1, recipeId);
		
		int rowCount = stm.executeUpdate();
		
		return rowCount > 0;
	}

}
