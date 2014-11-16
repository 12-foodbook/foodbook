package th.ac.kmitl.it.foodbook.beans;

public class RecipeStepPhoto {
    
    private long recipe_step_photo_id;
    private String photo_url;
    private long recipe_step_id;
    
    public long getRecipe_step_photo_id() {
        return recipe_step_photo_id;
    }
    
    public void setRecipe_step_photo_id(long recipe_step_photo_id) {
        this.recipe_step_photo_id = recipe_step_photo_id;
    }
    
    public String getPhoto_url() {
        return photo_url;
    }
    
    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
    
    public long getRecipe_step_id() {
        return recipe_step_id;
    }
    
    public void setRecipe_step_id(long recipe_step_id) {
        this.recipe_step_id = recipe_step_id;
    }
    
}
