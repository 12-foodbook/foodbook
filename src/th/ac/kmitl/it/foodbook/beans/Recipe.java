package th.ac.kmitl.it.foodbook.beans;

public class Recipe {

	private long recipe_id;
	private String name;
	private String video_url;
	private long user_id;
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}