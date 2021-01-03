package models;

import java.util.List;

import com.google.gson.JsonObject;
import application.ConnectionSingleton;
import application.H;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.stage.Stage;
interface BookInterface {
	public void initialize();
}
public class Book implements BookInterface {
	private SimpleIntegerProperty bookID = new SimpleIntegerProperty();
	private SimpleStringProperty author = new SimpleStringProperty();
	private SimpleIntegerProperty stock = new SimpleIntegerProperty();
	private SimpleStringProperty title = new SimpleStringProperty();
	private List<Record> records;
	private List<Review> reviews;

	private Button requestBtn;
	
	public Button getRequestBtn() {
		return requestBtn;
	}

	public void initRequestBtn() {
		this.requestBtn = new Button();
		this.requestBtn.setText("Request");
		if(stock.get() < 1) this.requestBtn.setDisable(true);
		this.requestBtn.setOnAction(e -> {
			H.puts(stock.toString());
			this.stockSubstract(1);
			H.puts(stock.toString());
			
			Button btn = (Button) e.getSource();
			if(stock.get() < 1) this.requestBtn.setDisable(true);

			Stage stage = (Stage) btn.getScene().getWindow();
			User u = (User) stage.getUserData();
			JsonObject serverResponse = null;
			try {
				JsonObject obj = H.buildJsonObj(List.of(
						"bookID", getBookIDProperty().asString().get(),
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
				this.stockAdd(1);
			}
		});
	}

	private void stockAdd(int i) {
		stock.set(stock.add(i).get());
	}

	private void stockSubstract(int i) {
		stock.set(stock.subtract(i).get());
	}

	public void initialize() {
		initRequestBtn();
	}
	
	public Book() {
		super();
	}
	
	public Book(int bookID, String author, int stock, String title) {
		super();
		this.bookID.set(bookID);
		this.author.set(author);
		this.stock.set(stock);
		this.title.set(title);
		this.initialize();
	}

	public SimpleIntegerProperty getBookIDProperty() {
		return this.bookID;
	}
	
	public void setBookID(int bookID) {
		this.bookID.set(bookID);
	}
	
	public int getBookID() {
		return this.bookID.get();
	}

	public StringProperty getAuthorProperty() {
		return this.author;
	}
	
	public String getAuthor() {
		return this.author.get();
	}

	public void setAuthor(String author) {
		this.author.set(author);
	}

	public SimpleIntegerProperty getStockProperty() {
		return this.stock;
	}
	public int getStock() {
		return this.stock.get();
	}

	public void setStock(int stock) {
		this.stock.set(stock);
	}

	public StringProperty getTitleProperty() {
		return this.title;
	}
	
	public String getTitle() {
		return this.title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
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
