package controllers;

import java.util.List;

import com.google.gson.JsonObject;

import application.ConnectionSingleton;
import application.HelperClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserView {
	@FXML
	private Label menuLabel;
	
	
	
	@FXML
	void initialize() {
		System.out.println("User interface is running!");
		JsonObject obj = HelperClass.buildJsonObj(List.of("filter", "all"));
		JsonObject serverResponse = ConnectionSingleton.getInstance().get("getBooks", obj);
	}



	private void buildJsonObj(List<String> of) {
		// TODO Auto-generated method stub
		
	}
}
