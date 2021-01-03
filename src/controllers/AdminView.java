package controllers;

import java.io.IOException;
import java.util.Date;

import application.G;
import application.H;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.RecordForAdmin;
import models.User;


public class AdminView {
	
    @FXML
    private TableView<RecordForAdmin> statusTable;

    @FXML
    private TableColumn<RecordForAdmin, String> statusBookNameCol;

    @FXML
    private TableColumn<RecordForAdmin, Date> dateCol;

    @FXML
    private TableColumn<RecordForAdmin, Integer> statusCol;
    
    @FXML
    private TableColumn<RecordForAdmin, ComboBox<String>> actionCol;
	
    @FXML
    private Tab settingstTab;
    
    @FXML
    private Button logoutBtn;
	
    @FXML
    void logoutUser(ActionEvent e) {
    	H.logoutUser(e, logoutBtn);
    }
    
	@FXML
	void initialize() throws Exception {
		H.puts("Admin interface is running!");
		initRecordsTableForAdmin(G.getRecordsForAdminAsObservableList("All"));
	}    
    
	private void initRecordsTableForAdmin(ObservableList<RecordForAdmin> observableList) {
		H.puts(observableList.toString());
		initRecordsTableCells();
		statusTable.setItems(observableList);
	}

	private void initRecordsTableCells() {
		statusBookNameCol.setCellValueFactory(new PropertyValueFactory<RecordForAdmin, String>("bookName"));
		dateCol.setCellValueFactory(new PropertyValueFactory<RecordForAdmin, Date>("date"));
		statusCol.setCellValueFactory(new PropertyValueFactory<RecordForAdmin, Integer>("state"));
		actionCol.setCellValueFactory(new PropertyValueFactory<RecordForAdmin, ComboBox<String>>("changeState"));
//		statusBookNameCol.setCellValueFactory(cellData -> cellData.getValue().getBookName());
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
