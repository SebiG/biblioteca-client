package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
	private void onClickLogin(ActionEvent event) {
		System.out.println("Login btn click");
		JsonObject obj = HelperClass.buildJsonObj(List.of(
			"userName", this.userName.getText(),
			"password", this.password.getText()
		));
		JsonObject serverResponse = ConnectionSingleton.getInstance().get("login", obj);
		System.out.println("Raspuns " + serverResponse);
		if(serverResponse == null) {
			infoLabel.setText("Null error!");
			return;
		}

		if(serverResponse.has("message")) {
			infoLabel.setText(serverResponse.get("message").getAsString());
		}
		if(serverResponse.has("userRole")) {
			if(serverResponse.get("userRole").getAsString().equals("user")) {
				this.openView("UserView.fxml");
			}
		}
	}
	
	private void openView(String viewName) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/" + viewName));
            Stage stage = new Stage();
            stage.setTitle("Interfata Biblioteca");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            // close login window
            Stage loginStage = (Stage) btnLogin.getScene().getWindow();
            loginStage.close();
            //open new view
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}

}
