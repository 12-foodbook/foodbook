package th.ac.kmitl.it.foodbook.beans;

public class RecipeStep {
	
	private long recipe_step_id;
	private String title;
	private String description;
	private long recipe_id;
	
	public long getRecipe_step_id() {
		return recipe_step_id;
	}
	
	public void setRecipe_step_id(long recipe_step_id) {
		this.recipe_step_id = recipe_step_id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public long getRecipe_id() {
		return recipe_id;
	}
	
	public void setRecipe_id(long recipe_id) {
		this.recipe_id = recipe_id;
	}

	@Override
	public String toString() {
		return "RecipeStep [recipe_step_id=" + recipe_step_id + ", title="
				+ title + ", description=" + description + ", recipe_id="
				+ recipe_id + "]";
	}

}
