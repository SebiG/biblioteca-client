package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;

import application.Adapters;
import application.G;
import application.H;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import view.models.Book;
import view.models.User;

public class Reviews {
	
	Book book;
	static Stage stage;
	 
	@FXML
	private ListView<String> reviewsForBook;
	
    @FXML
    private TextArea formTextReview;

    @FXML
    private Button formAddBtnReview;
        
	@FXML
	void initialize() throws Exception {
		H.puts("Reviews interface is running!");
		formAddBtnReviewAction();
	}   
    
    private void formAddBtnReviewAction() {
    	formAddBtnReview.setOnAction(e -> {
			H.puts(formTextReview.getText());
			addNewReview();
		});
	}

    private void addNewReview() {
		@SuppressWarnings("unchecked")
		HashMap<String, Object> hm = (HashMap<String, Object>) stage.getUserData();
		User u = (User) hm.get("user");
    	JsonArray response = G.putReview(List.of(
        		"review", formTextReview.getText(),
        		"userID", String.valueOf(u.getUserID()),
        		"bookID", String.valueOf(book.getBookID()) 
        	));
        	String r = Adapters.adaptReviewAsString(response.get(0).getAsJsonObject()); //TODO: check if response ok
        	System.out.println(r);
        	//TODO: add book to observable list
        	
        	//on success
        	formTextReview.setText("");
        	reviewsForBook.getItems().add(r);
    }
    
	private void initReviewsList(ObservableList<String> reviewsToStringAsObservableList) {
    	reviewsForBook.setItems(reviewsToStringAsObservableList);
	}

	public static void run(Book book, User user) {
		openView("Reviews.fxml", book, user);
	}

	public void initReviewsListFor(Book book) {
		this.book = book;
		initReviewsList(G.reviewsToStringAsObservableList(book.getBookID()));
	}
	
	private static void openView(String viewName, Book book, User user) {
		// TODO Auto-generated method stub
        Parent root;
        try {
        	FXMLLoader loader = new FXMLLoader(UserView.class.getResource("/view/" + viewName));
            root = loader.load();
            Reviews reviews = loader.getController();
            reviews.initReviewsListFor(book);
            stage = new Stage();
            stage.setTitle("Reviews for " + book.getTitle());
            stage.setScene(new Scene(root, 650, 400));
            stage.setResizable(false);
            HashMap<String,Object> hm = new HashMap<String,Object>();
            hm.put("user", user);
            stage.setUserData(hm);
            //open new view
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	

}
