package th.ac.kmitl.it.foodbook.beans;

public class Ingredient {
	
	private long ingredient_id;
	private String name;
	private String photo_url;
	
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
	
	public void setPhoto_url(String video_url) {
		this.photo_url = video_url;
	}

}
