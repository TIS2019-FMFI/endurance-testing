package DataProcessing;

public class Log {
	String User;
	String Updated_what;
	String Updated_how;
	String Updated_at;
	
	public Log(String user, String updated_what, String updated_how, String updated_at) {
		User = user;
		Updated_what = updated_what;
		Updated_how = updated_how;
		Updated_at = updated_at;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getUpdated_what() {
		return Updated_what;
	}

	public void setUpdated_what(String updated_what) {
		Updated_what = updated_what;
	}

	public String getUpdated_how() {
		return Updated_how;
	}

	public void setUpdated_how(String updated_how) {
		Updated_how = updated_how;
	}

	public String getUpdated_at() {
		return Updated_at;
	}

	public void setUpdated_at(String updated_at) {
		Updated_at = updated_at;
	}
	
}
