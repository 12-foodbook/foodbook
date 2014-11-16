package th.ac.kmitl.it.foodbook.beans;

public class RecipeCategory {
    
    private long recipe_category_id;
    private String name;
    
    public long getRecipe_category_id() {
        return recipe_category_id;
    }
    
    public void setRecipe_category_id(long recipe_category_id) {
        this.recipe_category_id = recipe_category_id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
}
