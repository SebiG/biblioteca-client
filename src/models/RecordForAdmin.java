package models;

import java.util.Date;

import javafx.scene.control.ComboBox;

public class RecordForAdmin extends Record {
	
	ComboBox<String> changeStateCbx;
	
	public RecordForAdmin(int recordID, Integer state, String bookName, Date date, String userID) {
		super(recordID, state, bookName, date, userID);
	}
	
	public ComboBox<String> getChangeStateCbx() {
		return changeStateCbx;
	}

	public void setChangeStateCbx(ComboBox<String> cbx) {
		this.changeStateCbx = cbx;
	}

}
