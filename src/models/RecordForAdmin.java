package models;

import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class RecordForAdmin extends Record {
	
	ComboBox<String> changeStateCbx;
	
	ObservableList<String> options = 
		    FXCollections.observableArrayList(
		        "Rezervata",
		        "Imprumutata",
		        "Returnata"
		    );
	public RecordForAdmin(int recordID, Integer state, String bookName, Date date, String userID) {
		super(recordID, state, bookName, date, userID);
		this.changeStateCbx = new ComboBox<String>(options);
		this.changeStateCbx.setValue(super.generateStateLabel(state));
	}

	public ComboBox<String> getChangeState() {
		return changeStateCbx;
	}

	public void setChangeState(ComboBox<String> changeState) {
		this.changeStateCbx = changeState;
	}

}
