package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.Foodbook;
import th.ac.kmitl.it.foodbook.beans.User;

public class UserDAO extends DAO {

	public UserDAO(DataSource ds) {
		super(ds);
	}

	public boolean create(User user) throws SQLException {
		Connection conn = ds.getConnection();
		String sql = "INSERT INTO users (username, hashed_password, salt) VALUES (?, ?, ?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1, user.getUsername());
		stm.setString(2, user.getHashed_password());
		stm.setString(3, user.getSalt());
		
		int rowCount = stm.executeUpdate();
		
		conn.close();
		return rowCount == 1;
	}

	public User authenticate(String username, String password) throws SQLException {
		User user = findByUsername(username);
		if (user == null) { return user; }
		
		byte[] salt = null;
		salt = Foodbook.stringToBytes(user.getSalt());
		
		byte[] hashedPasswordBytes = null;
		String hashedPassword = null;
		hashedPasswordBytes = Foodbook.hashPassword(password, salt);
		hashedPassword = Foodbook.bytesToString(hashedPasswordBytes);
		
		Connection conn = ds.getConnection();
		String sql = "SELECT * FROM users WHERE username = ? AND hashed_password = ? LIMIT 1";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1, username);
		stm.setString(2, hashedPassword);
		
		ResultSet rs = stm.executeQuery();
		
		user = null;
		
		if (rs.next()) {
			user = new User();
			user.setUser_id(rs.getLong("user_id"));
			user.setUsername(rs.getString("username"));
			user.setHashed_password(rs.getString("hashed_password"));
			user.setSalt(rs.getString("salt"));
		}
		
		conn.close();
		return user;
	}
	
	public User find(long id) throws SQLException {
		User user = null;
		
		Connection conn = ds.getConnection();
		String sql = "SELECT * FROM users WHERE user_id = ? LIMIT 1";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setLong(1, id);
		
		ResultSet rs = stm.executeQuery();
		
		if (rs.next()) {
			user = new User();
			user.setUser_id(rs.getLong("user_id"));
			user.setUsername(rs.getString("username"));
			user.setHashed_password(rs.getString("hashed_password"));
			user.setSalt(rs.getString("salt"));
		}
		
		conn.close();
		return user;
	}
	
	public User findByUsername(String username) throws SQLException {
		User user = null;
		
		Connection conn = ds.getConnection();
		String sql = "SELECT * FROM users WHERE username = ? LIMIT 1";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1, username);
		
		ResultSet rs = stm.executeQuery();
		
		if (rs.next()) {
			user = new User();
			user.setUser_id(rs.getLong("user_id"));
			user.setUsername(rs.getString("username"));
			user.setHashed_password(rs.getString("hashed_password"));
			user.setSalt(rs.getString("salt"));
		}
		
		conn.close();
		return user;
	}

}
