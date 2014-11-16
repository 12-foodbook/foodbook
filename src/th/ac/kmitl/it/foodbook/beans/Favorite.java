package th.ac.kmitl.it.foodbook.beans;

public class Favorite {
    
    private long user_id;
    private long recipe_id;
    
    public long getUser_id() {
        return user_id;
    }
    
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    
    public long getRecipe_id() {
        return recipe_id;
    }
    
    public void setRecipe_id(long recipe_id) {
        this.recipe_id = recipe_id;
    }
    
}
