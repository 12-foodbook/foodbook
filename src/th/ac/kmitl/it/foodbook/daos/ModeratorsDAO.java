package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import th.ac.kmitl.it.foodbook.PasswordManager;
import th.ac.kmitl.it.foodbook.beans.Moderator;

public class ModeratorsDAO extends AbstractDAO {

	public ModeratorsDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Moderator mod) throws SQLException {
		String sql = "INSERT INTO moderators (username, hashed_password, salt) VALUES (?, ?, ?)";
		PreparedStatement stm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		
		stm.setString(1, mod.getUsername());
		stm.setString(2, mod.getHashed_password());
		stm.setString(3, mod.getSalt());
		
		int rowCount = stm.executeUpdate();
		
		if (rowCount == 1) {
			ResultSet rs = stm.getGeneratedKeys();
			rs.next();
			mod.setModerator_id(rs.getLong(1));
			return true;
		}
		
		return false;
	}

	public Moderator authenticate(String username, String password) throws SQLException {
		Moderator mod = findByUsername(username);
		if (mod == null) { return mod; }
		
		byte[] salt = null;
		salt = PasswordManager.stringToBytes(mod.getSalt());
		
		byte[] hashedPasswordBytes = null;
		String hashedPassword = null;
		hashedPasswordBytes = PasswordManager.hashPassword(password, salt);
		hashedPassword = PasswordManager.bytesToString(hashedPasswordBytes);
		
		String sql = "SELECT * FROM moderators WHERE username = ? AND hashed_password = ? LIMIT 1";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1, username);
		stm.setString(2, hashedPassword);
		
		ResultSet rs = stm.executeQuery();
		
		mod = null;
		
		if (rs.next()) {
			mod = new Moderator();
			mod.setModerator_id(rs.getLong("moderator_id"));
			mod.setUsername(rs.getString("username"));
			mod.setHashed_password(rs.getString("hashed_password"));
			mod.setSalt(rs.getString("salt"));
		}
		
		return mod;
	}
	
	public Moderator find(long id) throws SQLException {
		Moderator mod = null;
		
		String sql = "SELECT * FROM moderators WHERE moderator_id = ? LIMIT 1";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setLong(1, id);
		
		ResultSet rs = stm.executeQuery();
		
		if (rs.next()) {
			mod = new Moderator();
			mod.setModerator_id(rs.getLong("user_id"));
			mod.setUsername(rs.getString("username"));
			mod.setHashed_password(rs.getString("hashed_password"));
			mod.setSalt(rs.getString("salt"));
		}
		
		return mod;
	}
	
	public Moderator findByUsername(String username) throws SQLException {
		Moderator mod = null;
		
		String sql = "SELECT * FROM moderators WHERE username = ? LIMIT 1";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1, username);
		
		ResultSet rs = stm.executeQuery();
		
		if (rs.next()) {
			mod = new Moderator();
			mod.setModerator_id(rs.getLong("user_id"));
			mod.setUsername(rs.getString("username"));
			mod.setHashed_password(rs.getString("hashed_password"));
			mod.setSalt(rs.getString("salt"));
		}
		
		return mod;
	}

}
