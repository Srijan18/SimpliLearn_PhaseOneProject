package com.dev.srijan.app;

public class UserFormat {
	
	private String username;
	private String password;
	
	public UserFormat() {}

	public UserFormat(String username, String password) {
		this.username = username;
		this.password = password;
	}
	//Generating getters and setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	//override to-string
	@Override
	public String toString() {
		return "Users [username=" + username + ", password=" + password + "]";
	}

	
	
	

}
