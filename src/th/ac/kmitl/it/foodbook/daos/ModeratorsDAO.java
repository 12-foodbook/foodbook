package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.Moderator;
import th.ac.kmitl.it.foodbook.utils.Util;

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
        salt = Util.stringToBytes(mod.getSalt());
        
        byte[] hashedPasswordBytes = null;
        String hashedPassword = null;
        hashedPasswordBytes = Util.hashPassword(password, salt);
        hashedPassword = Util.bytesToString(hashedPasswordBytes);
        
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
            mod.setModerator_id(rs.getLong("moderator_id"));
            mod.setUsername(rs.getString("username"));
            mod.setHashed_password(rs.getString("hashed_password"));
            mod.setSalt(rs.getString("salt"));
        }
        
        return mod;
    }
    
    public List<Moderator> findAll() throws SQLException {
        String sql = "SELECT * FROM moderators";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        ResultSet rs = stm.executeQuery();
        
        List<Moderator> moderators = new ArrayList<Moderator>();
        
        while (rs.next()) {
            Moderator moderator = new Moderator();
            moderator.setModerator_id(rs.getLong("moderator_id"));
            moderator.setUsername(rs.getString("username"));
            moderator.setHashed_password(rs.getString("hashed_password"));
            moderator.setSalt(rs.getString("salt"));
            moderators.add(moderator);
        }
        
        return moderators;
    }
    
    public Moderator findByUsername(String username) throws SQLException {
        Moderator mod = null;
        
        String sql = "SELECT * FROM moderators WHERE username = ? LIMIT 1";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, username);
        
        ResultSet rs = stm.executeQuery();
        
        if (rs.next()) {
            mod = new Moderator();
            mod.setModerator_id(rs.getLong("moderator_id"));
            mod.setUsername(rs.getString("username"));
            mod.setHashed_password(rs.getString("hashed_password"));
            mod.setSalt(rs.getString("salt"));
        }
        
        return mod;
    }
    
    public boolean delete(long moderatorId) throws SQLException {
        RecipesDAO recipesDAO = new RecipesDAO(conn);
        recipesDAO.deleteByUserId(moderatorId);
        
        String sql = "DELETE FROM moderators WHERE moderator_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, moderatorId);
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    public boolean update(Moderator moderator) throws SQLException {
        String sql = "UPDATE moderators SET username = ?, hashed_password = ?, salt = ? WHERE moderator_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, moderator.getUsername());
        stm.setString(2, moderator.getHashed_password());
        stm.setString(3, moderator.getSalt());
        stm.setLong(4, moderator.getModerator_id());
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
}
