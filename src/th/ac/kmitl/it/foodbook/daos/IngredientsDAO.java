package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.Ingredient;
import th.ac.kmitl.it.foodbook.beans.Recipe;

public class IngredientsDAO extends AbstractDAO {

	public IngredientsDAO(Connection conn) {
		super(conn);
	}
	
	public Ingredient find(long id) {
		Ingredient ingredient = null;
		
		
		return ingredient;
	}
	
	public List<Recipe> findRecipes(long id) throws SQLException {
		List<Recipe> recipes = new ArrayList<Recipe>();
		
		String sql = "SELECT recipe_id FROM recipes_ingredients WHERE ingradient_id = ?";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setLong(1, id);
		
		ResultSet rs = stm.executeQuery();
		
		RecipesDAO recipesDAO = new RecipesDAO(conn);
		
		while (rs.next()) {
			long recipeId = rs.getLong("recipe_id");
			Recipe recipe = recipesDAO.find(recipeId);
			recipes.add(recipe);
		}
		
		return recipes;
	}
	
	public int countRecipes(long id) throws SQLException {
		int count = -1;
		
		String sql = "SELECT COUNT(recipe_id) FROM recipes_ingredients WHERE ingradient_id = ?";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setLong(1, id);
		
		ResultSet rs = stm.executeQuery();
		
		if (rs.next()) {
			count = rs.getInt(1);
		}
		
		return count;
	}

}
