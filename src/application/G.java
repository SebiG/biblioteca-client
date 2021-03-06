package application;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.collections.ObservableList;
import view.models.Book;
import view.models.Record;
import view.models.RecordForAdmin;

//API calls
public abstract class G {
	static JsonObject obj;
	
	public static ObservableList<Book> getBooksAsObservableList() {
		return Adapters.booksToObservableList(getBooks());
	}
	
	public static ObservableList<Record> getRecordsAsObservableList(String userID) {
		return Adapters.recordsToObservableList(getRecordsFor(userID));
	}
	
	public static ObservableList<RecordForAdmin> getRecordsForAdminAsObservableList(String userID) {
		return Adapters.recordsForAdminToObservableList(getRecordsFor(userID));
	}
	
	public static JsonArray getBooks() {
		obj = H.buildJsonObj(List.of("filter", "all"));
		JsonArray serverResponse = null;
		try {
			serverResponse = ConnectionSingleton.getInstance().gets("getBooks", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverResponse;
	}

	public static JsonArray getRecordsFor(String userID) {
		obj = H.buildJsonObj(List.of("userID", userID));
		JsonArray serverResponse = null;
		try {
			serverResponse = ConnectionSingleton.getInstance().gets("getRecords", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverResponse;
	}

	public static JsonArray putBook(List<String> book) {
		obj = H.buildJsonObj(book);
		JsonArray serverResponse = null;
		try {
			serverResponse = ConnectionSingleton.getInstance().gets("putBook", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverResponse;
	}

	public static ObservableList<String> reviewsToStringAsObservableList(int bookID) {
		return Adapters.reviewsToStringAsObservableList(getReviewsFor(bookID));
	}

	public static JsonArray getReviewsFor(int bookID) {
		obj = H.buildJsonObj(List.of("bookID", String.valueOf(bookID)));
		JsonArray serverResponse = null;
		try {
			serverResponse = ConnectionSingleton.getInstance().gets("getReviewsForBook", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverResponse;
	}

	public static JsonArray putReview(List<String> review) {
		obj = H.buildJsonObj(review);
		JsonArray serverResponse = null;
		try {
			serverResponse = ConnectionSingleton.getInstance().gets("putReviewForBook", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverResponse;
	}
}
