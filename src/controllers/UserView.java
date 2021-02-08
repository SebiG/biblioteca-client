package controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;

import application.ConnectionSingleton;
import application.G;
import application.H;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import view.models.Book;
import view.models.Record;
import view.models.User;

public class UserView {
	
	static Stage stage;
	
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
    private TableColumn<Record, String> statusCol;

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
		H.puts("User interface is running!");
		
		initBookTable(G.getBooksAsObservableList());
		initRecordsTable(G.getRecordsAsObservableList("All"));
	}
	
	private void initBookTable(ObservableList<Book> observableList) {
		H.puts(observableList.toString());
		initBookTableCells();
		observableList = addRowActionsForBook(observableList);
		listOfBooksTable.setItems(observableList);
	}

	private ObservableList<Book> addRowActionsForBook(ObservableList<Book> observableList) {
		observableList.forEach(book -> {
			Button btn = new Button();
			btn.setText("Request");
			if(book.getStock() < 1) btn.setDisable(true);
			btn.setOnAction(e -> {
				book.stockSubstract(1);
				if(book.getStock() < 1) btn.setDisable(true);
				User u = (User) stage.getUserData();
				JsonObject serverResponse = null;
				try {
					JsonObject obj = H.buildJsonObj(List.of(
						"bookID", book.getBookIDProperty().asString().get(),
						"userID", Integer.toString(u.getUserID())
						));
					serverResponse = ConnectionSingleton.getInstance().get("reserved", obj);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(serverResponse.has("message") && serverResponse.get("message").getAsString().equals("ok")) {
					initRecordsTable(G.getRecordsAsObservableList("All"));
				} else {
					book.stockAdd(1);
				}
			});
			book.setRequestBtn(btn);
		});
		return observableList;
	}

	private void initBookTableCells() {
		bookNameCol.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
		authorCol.setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());
		stockCol.setCellValueFactory(cellData -> cellData.getValue().getStockProperty().asObject());
		actionCol.setCellValueFactory(new PropertyValueFactory<Book, Button>("requestBtn"));
	}

	private void initRecordsTable(ObservableList<Record> observableList) {
		H.puts(observableList.toString());
		initRecordsTableCells();
		statusTable.setItems(observableList);
	}

	private void initRecordsTableCells() {
		statusBookNameCol.setCellValueFactory(new PropertyValueFactory<Record, String>("bookName"));
		dateCol.setCellValueFactory(new PropertyValueFactory<Record, Date>("date"));
		statusCol.setCellValueFactory(cellData -> cellData.getValue().getStateProperty());
	}

	public static void run(User u) {
		openView("UserView.fxml", u);
	}
	
	private static void openView(String viewName, User user) {
        Parent root;
        try {
        	FXMLLoader loader = new FXMLLoader(UserView.class.getResource("/view/" + viewName));
            root = loader.load();
            stage = new Stage();
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
