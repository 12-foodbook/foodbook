package th.ac.kmitl.it.foodbook.beans;

import java.util.List;

public class Ingredient {
    
    private long ingredient_id;
    private String name;
    private String photo_url;
    private float calorie;
    private float amount;
    private String unit;
    private List<IngredientCategory> ingredient_categories;
    
    
    public List<IngredientCategory> getIngredient_categories() {
        return ingredient_categories;
    }

    
    public void setIngredient_categories(List<IngredientCategory> ingrdient_categories) {
        this.ingredient_categories = ingrdient_categories;
    }

    public long getIngredient_id() {
        return ingredient_id;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public void setIngredient_id(long ingredient_id) {
        this.ingredient_id = ingredient_id;
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
    
    public float getCalorie() {
        return Math.round(calorie);
    }
    
    public void setCalorie(float calorie) {
        this.calorie = calorie;
    }
    
    public float getAmount() {
        return amount;
    }
    
    public void setAmount(float amount) {
        this.amount = amount;
    }
    
    @Override
    public String toString() {
        return "Ingredient [ingredient_id=" + ingredient_id + ", name=" + name + ", photo_url=" + photo_url + ", calorie=" + calorie + ", amount=" + amount + ", unit=" + unit + "]";
    }
    
}
