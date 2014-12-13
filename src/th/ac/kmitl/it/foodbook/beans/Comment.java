package th.ac.kmitl.it.foodbook.beans;


public class Comment {
    
    private long comment_id;
    private String text;
    private long recipe_id;
    private long user_id;
    
    public long getComment_id() {
        return comment_id;
    }
    
    public void setComment_id(long comment_id) {
        this.comment_id = comment_id;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public long getRecipe_id() {
        return recipe_id;
    }
    
    public void setRecipe_id(long recipe_id) {
        this.recipe_id = recipe_id;
    }
    
    public long getUser_id() {
        return user_id;
    }
    
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    
}
