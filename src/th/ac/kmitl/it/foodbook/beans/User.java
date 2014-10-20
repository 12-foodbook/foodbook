package th.ac.kmitl.it.foodbook.beans;

public class User {
	
	private long user_id;
	private String username;
	private String hashed_password;
	private String salt;

	public long getUser_id() {
		return user_id;
	}
	
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getHashed_password() {
		return hashed_password;
	}
	
	public void setHashed_password(String hashed_password) {
		this.hashed_password = hashed_password;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}

}
