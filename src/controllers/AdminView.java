package controllers;

import java.io.IOException;
import java.util.Date;

import application.H;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import models.Record;
import models.User;


public class AdminView {
	
    @FXML
    private TableView<Record> statusTable;

    @FXML
    private TableColumn<Record, String> statusBookNameCol;

    @FXML
    private TableColumn<Record, Date> dateCol;

    @FXML
    private TableColumn<Record, Integer> statusCol;
	
    @FXML
    private Tab settingstTab;
    
    @FXML
    private Button logoutBtn;
	
    @FXML
    void logoutUser(ActionEvent e) {
    	H.logoutUser(e, logoutBtn);
    }
    
	public static void run(User u) {
		openView("AdminView.fxml", u);
	}
	
	private static void openView(String viewName, User user) {
        Parent root;
        try {
        	FXMLLoader loader = new FXMLLoader(UserView.class.getResource("/view/" + viewName));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Interfata Administrator Biblioteca - " + user.getUserName());
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.setUserData(user);
            //open new view
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
}
