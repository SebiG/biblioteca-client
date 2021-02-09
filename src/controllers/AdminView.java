package controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import application.Adapters;
import application.ConnectionSingleton;
import application.G;
import application.H;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import view.models.Book;
import view.models.RecordForAdmin;
import view.models.User;


public class AdminView {
	static Stage stage;
	
    @FXML
    private TableView<RecordForAdmin> statusTable;

    @FXML
    private TableColumn<RecordForAdmin, String> statusBookNameCol;

    @FXML
    private TableColumn<RecordForAdmin, Date> dateCol;

    @FXML
    private TableColumn<RecordForAdmin, String> statusCol;
    
    @FXML
    private TableColumn<RecordForAdmin, ComboBox<String>> actionCol;
    
    @FXML // fx:id="formBookTitle"
    private TextField formBookTitle;

    @FXML // fx:id="formBookAuthors"
    private TextField formBookAuthors;

    @FXML // fx:id="formBookStock"
    private TextField formBookStock;

    @FXML // fx:id="formNewBookSubmit"
    private Button formNewBookSubmit;

    @FXML
    private Label addBookFormTitle;
    
    @FXML
    void addNewBook(ActionEvent event) {
    	JsonArray response = G.putBook(List.of(
    		"title", formBookTitle.getText(),
    		"authors", formBookAuthors.getText(),
    		"stock", formBookStock.getText() //validate number int
    	));
    	Book book = Adapters.adaptBook(response.get(0).getAsJsonObject()); //TODO: check if response ok
    	System.out.println(book);
    	//TODO: add book to observable list
    	
    	//on success
    	formBookStock.setText("");
    	formBookTitle.setText("");
    	formBookAuthors.setText("");
    	addBookFormTitle.setText("Book added, add new book:");
    }
	
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
		observableList = addRowActionsForRecords(observableList);
		statusTable.setItems(observableList);
	}

	private ObservableList<RecordForAdmin> addRowActionsForRecords(ObservableList<RecordForAdmin> observableList) {
		observableList.forEach(record -> {
			ObservableList<String> options = 
				    FXCollections.observableArrayList(
				        "Rezervata",
				        "Imprumutata",
				        "Returnata"
		    );
			
			ComboBox<String> cbx = new ComboBox<String>(options);
			cbx.setValue(record.getState());
			
			cbx.setOnAction(e -> {
				JsonObject serverResponse = null;
				User u = (User) stage.getUserData();
				try {
					JsonObject obj = H.buildJsonObj(List.of(
						"recordID", String.valueOf(record.getRecordID()),
						"userID", Integer.toString(u.getUserID()),
						"state", record.convertLabelToID(cbx.getValue()).toString()
						));
					serverResponse = ConnectionSingleton.getInstance().get("setStateForRecord", obj);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(serverResponse.has("message") && serverResponse.get("message").getAsString().equals("ok")) {
					record.setState(cbx.getValue());
				} else {
					// TODO Show user an error message
				}
			});  
			record.setChangeStateCbx(cbx);
		});
		return observableList;
	}

	private void initRecordsTableCells() {
		statusBookNameCol.setCellValueFactory(new PropertyValueFactory<RecordForAdmin, String>("bookName"));
		dateCol.setCellValueFactory(new PropertyValueFactory<RecordForAdmin, Date>("date"));
		statusCol.setCellValueFactory(cellData -> cellData.getValue().getStateProperty());
		actionCol.setCellValueFactory(new PropertyValueFactory<RecordForAdmin, ComboBox<String>>("changeStateCbx"));
	}

	public static void run(User u) {
		openView("AdminView.fxml", u);
	}
	
	private static void openView(String viewName, User user) {
        Parent root;
        try {
        	FXMLLoader loader = new FXMLLoader(UserView.class.getResource("/view/" + viewName));
            root = loader.load();
            stage = new Stage();
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
