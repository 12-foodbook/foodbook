package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.utils.Util;

public class UsersDAO extends AbstractDAO {
    
    public UsersDAO(Connection conn) {
        super(conn);
    }
    
    public boolean create(User user) throws SQLException {
        String sql = "INSERT INTO users (username, hashed_password, salt) VALUES (?, ?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        
        stm.setString(1, user.getUsername());
        stm.setString(2, user.getHashed_password());
        stm.setString(3, user.getSalt());
        
        int rowCount = stm.executeUpdate();
        
        if (rowCount == 1) {
            ResultSet rs = stm.getGeneratedKeys();
            rs.next();
            user.setUser_id(rs.getLong(1));
            return true;
        }
        
        return false;
    }
    
    public User authenticate(String username, String password) throws SQLException {
        User user = findByUsername(username);
        if (user == null) { return user; }
        
        byte[] salt = null;
        salt = Util.stringToBytes(user.getSalt());
        
        byte[] hashedPasswordBytes = null;
        String hashedPassword = null;
        hashedPasswordBytes = Util.hashPassword(password, salt);
        hashedPassword = Util.bytesToString(hashedPasswordBytes);
        
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
        
        return user;
    }
    
    public User find(long id) throws SQLException {
        User user = null;
        
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
        
        return user;
    }
    
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM users";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        ResultSet rs = stm.executeQuery();
        
        List<User> users = new ArrayList<User>();
        
        while (rs.next()) {
            User user = new User();
            user.setUser_id(rs.getLong("user_id"));
            user.setUsername(rs.getString("username"));
            user.setHashed_password(rs.getString("hashed_password"));
            user.setSalt(rs.getString("salt"));
            users.add(user);
        }
        
        return users;
    }
    
    public User findByUsername(String username) throws SQLException {
        User user = null;
        
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
        
        return user;
    }
    
    public boolean delete(long userId) throws SQLException {
        FavoritesDAO favoritesDAO = new FavoritesDAO(conn);
        favoritesDAO.deleteByUserId(userId);
        
        RatesDAO ratesDAO = new RatesDAO(conn);
        ratesDAO.deleteByUserId(userId);
        
        RecipesDAO recipesDAO = new RecipesDAO(conn);
        recipesDAO.deleteByUserId(userId);
        
        String sql = "DELETE FROM users WHERE user_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, userId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }

    public boolean update(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, hashed_password = ?, salt = ? WHERE user_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, user.getUsername());
        stm.setString(2, user.getHashed_password());
        stm.setString(3, user.getSalt());
        stm.setLong(4, user.getUser_id());
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
}
