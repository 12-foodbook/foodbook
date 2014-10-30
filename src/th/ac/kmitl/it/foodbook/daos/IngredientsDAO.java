package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.Ingredient;

public class IngredientsDAO extends AbstractDAO {

	public IngredientsDAO(Connection conn) {
		super(conn);
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
		}
		
		return ingredient;
	}
	
	public List<Ingredient> findByRecipeId(long recipeId) throws SQLException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		String sql = "SELECT ingredient_id FROM recipes_ingredients WHERE recipe_id = ?";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setLong(1, recipeId);
		
		ResultSet rs = stm.executeQuery();
		
		IngredientsDAO ingredientsDAO = new IngredientsDAO(conn);
		
		while (rs.next()) {
			long ingredientId = rs.getLong("ingredient_id");
			Ingredient ingredient = ingredientsDAO.find(ingredientId);
			ingredients.add(ingredient);
		}
		
		return ingredients;
	}
	
	public int countRecipes(long id) throws SQLException {
		int count = -1;
		
		String sql = "SELECT COUNT(recipe_id) FROM recipes_ingredients WHERE ingredient_id = ?";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setLong(1, id);
		
		ResultSet rs = stm.executeQuery();
		
		if (rs.next()) {
			count = rs.getInt(1);
		}
		
		return count;
	}

}
