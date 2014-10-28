package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.Ingredient;
import th.ac.kmitl.it.foodbook.beans.Recipe;

public class RecipesDAO extends AbstractDAO {

	public RecipesDAO(Connection conn) {
		super(conn);
	}
	
	public boolean create(Recipe recipe) throws SQLException {
		String sql = "INSERT INTO recipes (name, video_url, user_id) VALUES (?, ?, ?)";
		PreparedStatement stm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		
		stm.setString(1, recipe.getName());
		stm.setString(2, recipe.getVideo_url());
		stm.setLong(3, recipe.getUser_id());
		
		int rowCount = stm.executeUpdate();
		
		if (rowCount == 1) {
			ResultSet rs = stm.getGeneratedKeys();
			rs.next();
			recipe.setRecipe_id(rs.getLong(1));
			return true;
		}
		
		return false;
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
	
	public List<Ingredient> findIngredients(long id) throws SQLException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		String sql = "SELECT ingredient_id FROM recipes_ingredients WHERE recipe_id = ?";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setLong(1, id);
		
		ResultSet rs = stm.executeQuery();
		
		IngredientsDAO ingredientsDAO = new IngredientsDAO(conn);
		
		while (rs.next()) {
			long ingredientId = rs.getLong("ingredient_id");
			Ingredient ingredient = ingredientsDAO.find(ingredientId);
			ingredients.add(ingredient);
		}
		
		return ingredients;
	}

}
