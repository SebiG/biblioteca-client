package models;

import java.util.List;

import com.google.gson.JsonObject;
import application.ConnectionSingleton;
import application.H;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
interface BookInterface {
	public void initialize();
}
public class Book implements BookInterface {
	private int bookID;
	private String author;
	private int stock;
	private String title;
	private List<Record> records;
	private List<Review> reviews;
	
	//transient adaugat pentru a exclude procesarea cu gson 
	private transient Button requestBtn;
	
	public Button getRequestBtn() {
		return requestBtn;
	}

	public void initRequestBtn() {
		this.requestBtn = new Button();
		this.requestBtn.setText("Request");
		if(stock < 1) this.requestBtn.setDisable(true);
		this.requestBtn.setOnAction(e -> {
			this.setStock(stock - 1);
			Button btn = (Button) e.getSource();
			if(stock < 1) this.requestBtn.setDisable(true);
			@SuppressWarnings("unchecked")
			TableView<Book> tb = (TableView<Book>) btn.getScene().lookup("#listOfBooksTable");
			tb.refresh();
			Stage stage = (Stage) btn.getScene().getWindow();
			User u = (User) stage.getUserData();
			JsonObject serverResponse = null;
			try {
				JsonObject obj = H.buildJsonObj(List.of(
						"bookID", Integer.toString(getBookID()),
						"userID", Integer.toString(u.getUserID())
						));
				serverResponse = ConnectionSingleton.getInstance().get("reserved", obj);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(serverResponse.has("message") && serverResponse.get("message").getAsString().equals("ok")) {
				//TODO display to user that the request succeeded
			} else {
				this.setStock(stock + 1);
				tb.refresh();
			}
		});
	}

	public void initialize() {
		initRequestBtn();
	}
	
	public Book() {
		super();
	}
	
	public Book(int bookID, String author, int stock, String title) {
		super();
		this.bookID = bookID;
		this.author = author;
		this.stock = stock;
		this.title = title;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public int getBookID() {
		return this.bookID;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getStock() {
		return this.stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Record> getRecords() {
		return records;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

}
