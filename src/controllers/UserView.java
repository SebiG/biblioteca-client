package controllers;

import java.util.List;

//import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import application.ConnectionSingleton;
import application.H;
//import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Book;

public class UserView {
	
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
	void initialize() throws Exception {
		H.puts("User interface is running!");
		JsonObject obj = H.buildJsonObj(List.of("filter", "all"));
		JsonArray serverResponse = ConnectionSingleton.getInstance().gets("getBooks", obj);
		
		if(serverResponse == null) {
			throw new Exception("Null server response error!");
		}
		H.puts(serverResponse.toString());
		initBookTable(H.toObservableList(serverResponse, Book.class));
	}

	private void initBookTable(ObservableList<Book> observableList) {
		H.puts(observableList.toString());
		initBookTableCells();
		listOfBooksTable.setItems(observableList);
	}


	private void initBookTableCells() {
		bookNameCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		stockCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("stock"));
		actionCol.setCellValueFactory(new PropertyValueFactory<Book, Button>("requestBtn"));
	}

	
    // Observable list of books	
}
