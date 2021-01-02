package controllers;

import java.io.IOException;
import java.util.Date;
import application.G;
import application.H;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Book;
import models.Record;
import models.User;

public class UserView {
	
	@FXML
	private Tab ListTab;
	
    @FXML
    private TableView<Book> listOfBooksTable;

    @FXML
    private TableColumn<Book, String> bookNameCol;

    @FXML
    private TableColumn<Book, String> authorCol;

    @FXML
    private TableColumn<Book, Integer> stockCol;

    @FXML
    private TableColumn<Book, Button> actionCol;

    @FXML
    private Tab statusTab;

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
	void initialize() throws Exception {
		H.puts("User interface is running!");
		
		initBookTable(G.getBooksAsObservableList());
		initRecordsTable(G.getRecordsAsObservableList("2"));
	}
	
	private void initBookTable(ObservableList<Book> observableList) {
		H.puts(observableList.toString());
		initBookTableCells();
		listOfBooksTable.setItems(observableList);
	}

	private void initBookTableCells() {
		statusBookNameCol.setCellValueFactory(new PropertyValueFactory<Record, String>("bookName"));
		dateCol.setCellValueFactory(new PropertyValueFactory<Record, Date>("date"));
		statusCol.setCellValueFactory(new PropertyValueFactory<Record, Integer>("state"));
	}

	private void initRecordsTable(ObservableList<Record> observableList) {
		H.puts(observableList.toString());
		initRecordsTableCells();
		statusTable.setItems(observableList);
	}

	private void initRecordsTableCells() {
		bookNameCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		stockCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("stock"));
		actionCol.setCellValueFactory(new PropertyValueFactory<Book, Button>("requestBtn"));
	}

	public static void run(User u) {
		openView("UserView.fxml", u);
	}
	
	private static void openView(String viewName, User user) {
        Parent root;
        try {
        	FXMLLoader loader = new FXMLLoader(UserView.class.getResource("/view/" + viewName));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Interfata Biblioteca - " + user.getUserName());
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
