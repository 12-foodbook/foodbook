package th.ac.kmitl.it.foodbook.beans;

public class IngredientCategory {
    
    private long ingredient_category_id;
    private String name;
    
    public long getIngredient_category_id() {
        return ingredient_category_id;
    }
    
    public void setIngredient_category_id(long ingredient_category_id) {
        this.ingredient_category_id = ingredient_category_id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "IngredientCategory [ingredient_category_id=" + ingredient_category_id + ", name=" + name + "]";
    }
    
}
