package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.models.User;

import java.util.List;

import com.google.gson.JsonObject;


import application.ConnectionSingleton;
import application.HelperClass;

public class Login {
	
	@FXML
	private Button btnLogin;
	
	@FXML
	private TextField userName;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Label infoLabel;
	
	@FXML
	void initialize() {
		System.out.println("Login is running!");
	}
	
    @FXML
    void changeUser(MouseEvent event) {
    	if(userName.getText().equals("cititor")) {
    		userName.setText("bibliotecar");
    		password.setText("bibliotecar");
    	} else {
    		userName.setText("cititor");
    		password.setText("cititor");
    	}
    }
	
	@FXML
	private void onClickLogin(ActionEvent event) {
		System.out.println("Login btn click");
		JsonObject obj = HelperClass.buildJsonObj(List.of(
			"userName", this.userName.getText(),
			"password", this.password.getText()
		));
		JsonObject serverResponse = null;
		try {
			serverResponse = ConnectionSingleton.getInstance().get("login", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Raspuns " + serverResponse);
		
		
		if(serverResponse == null) {
			infoLabel.setText("Null error (login)!");
			return;
		}
		if(serverResponse.has("message")) {
			infoLabel.setText(serverResponse.get("message").getAsString());
		}
		if(serverResponse.has("userRole")) {
			// close login window
			Stage loginStage = (Stage) btnLogin.getScene().getWindow();
			loginStage.close();
			
			User u = new User();
			u.setUserID(serverResponse.get("userID").getAsInt());
			u.setUserName(serverResponse.get("userName").getAsString());		
			if(serverResponse.get("userRole").getAsString().equals("user")) {
				u.setRole("user");
				UserView.run(u);
			}
			if(serverResponse.get("userRole").getAsString().equals("admin")) {
				u.setRole("admin");
				AdminView.run(u);
			}
		}
	}
}
