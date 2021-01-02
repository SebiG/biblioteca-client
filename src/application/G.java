package application;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.collections.ObservableList;
import models.Book;
import models.Record;

//API calls
public abstract class G {
	static JsonObject obj;
	
	public static ObservableList<Book> getBooksAsObservableList() {
		return H.toObservableList(getBooks(), Book.class);
	}
	
	public static ObservableList<Record> getRecordsAsObservableList(String userID) {
		return H.toObservableList(getRecordsFor(userID), Record.class);
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
	
}
