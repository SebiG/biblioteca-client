package models;

import java.util.Date;

public class Record {
	private int recordID;
	private String state;
	private String bookName;
	private Date date;
	private String userID;
	
	public Record(int recordID, Integer state, String bookName, Date date, String userID) {
		super();
		this.recordID = recordID;
		this.state = generateStateLabel(state);
		this.bookName = bookName;
		this.date = date;
		this.userID = userID;
	}
	
	protected String generateStateLabel(Integer state) {
		String label = null;
		switch (state) {
			case 1:
				label = "Rezervata";
				break;
			case 2:
				label = "Imprumutata";
				break;
			case 0:
				label = "Returnata";
				break;
			default:
				break;
		}
		return label;
	}
	
	public int getRecordID() {
		return recordID;
	}
	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
}
