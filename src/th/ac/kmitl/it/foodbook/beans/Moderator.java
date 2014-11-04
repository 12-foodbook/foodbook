package th.ac.kmitl.it.foodbook.beans;

public class Moderator {
	private long moderator_id;
	private String username;
	private String hashed_password;
	private String salt;

	public long getModerator_id() {
		return moderator_id;
	}

	public void setModerator_id(long moderator_id) {
		this.moderator_id = moderator_id;
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
