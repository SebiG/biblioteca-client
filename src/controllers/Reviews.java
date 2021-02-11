package controllers;

import java.io.IOException;

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
	}   
    
    private void initReviewsList(ObservableList<String> reviewsToStringAsObservableList) {
    	reviewsForBook.setItems(reviewsToStringAsObservableList);
	}

	public static void run(Book book) {
		openView("Reviews.fxml", book);
	}

	public void initReviewsListFor(Book book) {
		this.book = book;
		initReviewsList(G.reviewsToStringAsObservableList(book.getBookID()));
	}
	
	private static void openView(String viewName, Book book) {
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
            //stage.setUserData(user);
            //open new view
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	

}
