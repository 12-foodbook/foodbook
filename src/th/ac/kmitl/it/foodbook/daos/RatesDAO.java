package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.Rate;

public class RatesDAO extends AbstractDAO {

	public RatesDAO(Connection conn) {
		super(conn);

	}

	public boolean create(Rate rate) throws SQLException {
		String sql = "INSERT INTO rates (user_id, recipe_id, rate) VALUES (?, ?, ?)";
		PreparedStatement stm = conn.prepareStatement(sql);

		stm.setLong(1, rate.getUser_id());
		stm.setLong(2, rate.getRecipe_id());
		stm.setLong(3, rate.getRate());

		int rowCount = stm.executeUpdate();

		if (rowCount == 1) {
			return true;
		}

		return false;
	}

	public boolean update(Rate rate) throws SQLException {
		String sql = "UPDATE rates SET rate = ? WHERE user_id = ? AND recipe_id = ?";
		PreparedStatement stm = conn.prepareStatement(sql);

		stm.setLong(1, rate.getRate());
		stm.setLong(2, rate.getUser_id());
		stm.setLong(3, rate.getRecipe_id());

		int rowCount = stm.executeUpdate();

		if (rowCount == 1) {
			return true;
		}

		return false;
	}

	public List<Rate> findByUserId(long userId) throws SQLException {
		List<Rate> rates = new ArrayList<Rate>();
		Rate rate = null;

		String sql = "SELECT * FROM rates WHERE user_id = ?";
		PreparedStatement stm = conn.prepareStatement(sql);

		stm.setLong(1, userId);

		ResultSet rs = stm.executeQuery();

		while (rs.next()) {
			rate = new Rate();
			rate.setRecipe_id(rs.getLong("recipe_id"));
			rate.setUser_id(rs.getLong("user_id"));
			rate.setRate(rs.getLong("rate"));
			rates.add(rate);
		}

		return rates;
	}
	
	public List<Rate> findByRecipeId(long recipeId) throws SQLException {
		List<Rate> rates = new ArrayList<Rate>();
		Rate rate = null;

		String sql = "SELECT * FROM rates WHERE recipe_id = ?";
		PreparedStatement stm = conn.prepareStatement(sql);

		stm.setLong(1, recipeId);

		ResultSet rs = stm.executeQuery();

		while (rs.next()) {
			rate = new Rate();
			rate.setRecipe_id(rs.getLong("recipe_id"));
			rate.setUser_id(rs.getLong("user_id"));
			rate.setRate(rs.getLong("rate"));
			rates.add(rate);
		}

		return rates;
	}

	public Rate find(long userId, long recipeId) throws SQLException {
		List<Rate> rates = new ArrayList<Rate>();
		rates = findByUserId(userId);
		for (Rate i : rates) {
			if (i.getRecipe_id() == recipeId) {
				return i;
			}
		}
		return null;
	}

}
