package th.ac.kmitl.it.foodbook.beans;

public class Recipe {
    
    private long recipe_id;
    private String name;
    private String photo_url;
    private String video_url;
    private long user_id;
    private boolean is_moderator_id;
    
    public long getRecipe_id() {
        return recipe_id;
    }
    
    public void setRecipe_id(long recipe_id) {
        this.recipe_id = recipe_id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhoto_url() {
        return photo_url;
    }
    
    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
    
    public String getVideo_url() {
        return video_url;
    }
    
    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }
    
    public long getUser_id() {
        return user_id;
    }
    
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    
    public boolean getIs_moderator_id() {
        return is_moderator_id;
    }
    
    public void setIs_moderator_id(boolean is_moderator_id) {
        this.is_moderator_id = is_moderator_id;
    }

    @Override
    public String toString() {
        return "Recipe [recipe_id=" + recipe_id + ", name=" + name + ", photo_url=" + photo_url + ", video_url=" + video_url + ", user_id=" + user_id + ", is_moderator_id=" + is_moderator_id + "]";
    }
    
}
