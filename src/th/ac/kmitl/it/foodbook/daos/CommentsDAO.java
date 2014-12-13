package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.ac.kmitl.it.foodbook.beans.Comment;


public class CommentsDAO extends AbstractDAO {

    public CommentsDAO(Connection conn) {
        super(conn);
    }
    
    public boolean create(Comment comment) throws SQLException {
        String sql = "INSERT INTO comments (text, recipe_id, user_id) VALUES (?, ?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, comment.getText());
        stm.setLong(2, comment.getRecipe_id());
        stm.setLong(3, comment.getUser_id());
        
        int rowCount = stm.executeUpdate();
        
        return rowCount == 1;
    }
    
    public List<Comment> findByRecipeId(long recipeId) throws SQLException {
        List<Comment> comments = new ArrayList<Comment>();
        Comment comment = null;
        
        String sql = "SELECT * FROM comments WHERE recipe_id = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setLong(1, recipeId);
        
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            comment = new Comment();
            comment.setComment_id(rs.getLong("comment_id"));
            comment.setText(rs.getString("text"));
            comment.setRecipe_id(rs.getLong("recipe_id"));
            comment.setUser_id(rs.getLong("user_id"));
            comments.add(comment);
        }
        
        return comments;
    }
    
}
