package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import application.ConnectionSingleton;

public class Login {
	@FXML
	private Button showUsers;

	@FXML
	void initialize() {
		System.out.println("Login is running!");

	}

	@FXML
	private void onShowUsersButtonClick(ActionEvent event) {
		System.out.println("Show Users Btn click");
		ConnectionSingleton.getInstance().get("login", "test");
	}

}
