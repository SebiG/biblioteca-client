package view.models;

import java.util.Date;

import javafx.beans.property.SimpleStringProperty;

public class Record {
	private int recordID;
	private SimpleStringProperty state = new SimpleStringProperty();
	private String bookName;
	private Date date;
	private String userID;
	
	public Record(int recordID, Integer state, String bookName, Date date, String userID) {
		super();
		this.recordID = recordID;
		this.state.set(generateStateLabel(state));
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
	
	public Integer convertLabelToID(String label) {
		Integer id = null;
		switch (label) {
			case "Rezervata":
				id = 1;
				break;
			case "Imprumutata":
				id = 2;
				break;
			case "Returnata":
				id = 0;
				break;
			default:
				break;
		}
		return id;
	}
	
	public int getRecordID() {
		return recordID;
	}
	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}
	
	public SimpleStringProperty getStateProperty() {
		return this.state;
	}
	
	public String getState() {
		return state.get();
	}
	public void setState(String stateStr) {
		this.state.set(stateStr);
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
