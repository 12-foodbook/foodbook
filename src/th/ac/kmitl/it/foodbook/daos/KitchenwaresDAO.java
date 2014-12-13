package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.Kitchenware;

public class KitchenwaresDAO extends AbstractDAO {
    
    public KitchenwaresDAO(Connection conn) {
        super(conn);
    }
    
    public Kitchenware find(long id) throws SQLException {
        Kitchenware kitchenware = null;
        
        String sql = "SELECT * FROM kitchenwares WHERE kitchenware_id = ? LIMIT 1";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, id);
        
        ResultSet rs = stm.executeQuery();
        
        if (rs.next()) {
            kitchenware = new Kitchenware();
            kitchenware.setKitchenware_id(rs.getLong("kitchenware_id"));
            kitchenware.setName(rs.getString("name"));
            kitchenware.setPhoto_url(rs.getString("photo_url"));
        }
        
        return kitchenware;
    }
    
    public List<Kitchenware> findByRecipeId(long recipeId) throws SQLException {
        List<Kitchenware> kitchenwares = new ArrayList<Kitchenware>();
        
        String sql = "SELECT kitchenware_id FROM recipes_kitchenwares WHERE recipe_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeId);
        
        ResultSet rs = stm.executeQuery();
        
        KitchenwaresDAO kitchenwaresDAO = new KitchenwaresDAO(conn);
        
        while (rs.next()) {
            long kitchenwareId = rs.getLong("kitchenware_id");
            Kitchenware kitchenware = kitchenwaresDAO.find(kitchenwareId);
            kitchenwares.add(kitchenware);
        }
        
        return kitchenwares;
    }
    
    public List<Kitchenware> findAll() throws SQLException {
        List<Kitchenware> kitchenwares = new ArrayList<Kitchenware>();
        
        String sql = "SELECT * FROM kitchenwares";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        ResultSet rs = stm.executeQuery();
        
        if (rs.next()) {
            Kitchenware kitchenware = find(rs.getLong("kitchenware_id"));
            kitchenwares.add(kitchenware);
        }
        
        return kitchenwares;
    }
    
    public boolean create(Kitchenware kitchenware) throws SQLException {
        String sql = "INSERT INTO kitchenwares (name, photo_url) VALUES (?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, kitchenware.getName());
        stm.setString(2, kitchenware.getPhoto_url());
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
}