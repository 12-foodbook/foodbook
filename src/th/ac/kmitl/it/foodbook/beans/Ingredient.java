package th.ac.kmitl.it.foodbook.beans;

public class Ingredient {
    
    private long ingredient_id;
    private String name;
    private String photo_url;
    private float calorie;
    private String amount;
    
    public long getIngredient_id() {
        return ingredient_id;
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
        return calorie;
    }

    
    public void setCalorie(float calorie) {
        this.calorie = calorie;
    }
    
    public String getAmount() {
        return amount;
    }
    
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
}
