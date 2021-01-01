package models;

public class User {
	private int userID;
	private String role;
	private String userName;
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserID() {
		return userID;
	}
	public String getRole() {
		return role;
	}
	public String getUserName() {
		return userName;
	}
}
