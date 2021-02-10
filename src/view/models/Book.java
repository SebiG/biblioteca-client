package view.models;

import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Book {
	private SimpleIntegerProperty bookID = new SimpleIntegerProperty();
	private SimpleStringProperty author = new SimpleStringProperty();
	private SimpleIntegerProperty stock = new SimpleIntegerProperty();
	private SimpleStringProperty title = new SimpleStringProperty();
	private List<Record> records;
	private List<Review> reviews;

	private Button requestBtn;
	private Button reviewViewBtn;
	private HBox actions;
	
	public Button getRequestBtn() {
		return requestBtn;
	}

	public void setRequestBtn(Button btn) {
		this.requestBtn = btn;
	}
	
	public void stockAdd(int i) {
		stock.set(stock.add(i).get());
	}

	public void stockSubstract(int i) {
		stock.set(stock.subtract(i).get());
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

	public Button getReviewViewBtn() {
		return reviewViewBtn;
	}

	public void setReviewViewBtn(Button reviewViewBtn) {
		this.reviewViewBtn = reviewViewBtn;
	}

	public HBox getActions() {
		return actions;
	}

	public void setActions(HBox actions) {
		this.actions = actions;
	}

}
