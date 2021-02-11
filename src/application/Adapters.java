package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.models.Book;
import view.models.Record;
import view.models.RecordForAdmin;
import view.models.Review;

public abstract class Adapters {
	public static ObservableList<Book> booksToObservableList(JsonArray books) {
		ObservableList<Book> list = FXCollections.observableArrayList();
		books.forEach(r -> {
			JsonObject jb = r.getAsJsonObject();
			list.add(
				adaptBook(jb)
			);
		});
		
		return list;
	}
	public static Book adaptBook(JsonObject jb) {
		return new Book(
				jb.get("bookID").getAsInt(), 
				jb.get("author").getAsString(), 
				jb.get("stock").getAsInt(), 
				jb.get("title").getAsString()
				);
	}
	public static ObservableList<Record> recordsToObservableList(JsonArray records) {
		ObservableList<Record> list = FXCollections.observableArrayList();
		records.forEach(r -> {
			JsonObject jr = r.getAsJsonObject();
			Date date = convertToDate(jr.get("date").getAsString());
			list.add(
				new Record(
					jr.get("recordID").getAsInt(), 
					jr.get("state").getAsInt(), 
					jr.get("book").getAsJsonObject().get("title").getAsString(), 
					date, 
					jr.get("user").getAsJsonObject().get("userID").getAsString()
				)
			);
		});
		
		return list;
	}
	
	public static ObservableList<RecordForAdmin> recordsForAdminToObservableList(JsonArray records) {
		ObservableList<RecordForAdmin> list = FXCollections.observableArrayList();
		records.forEach(r -> {
			JsonObject jr = r.getAsJsonObject();
			Date date = convertToDate(jr.get("date").getAsString());

			list.add(
				new RecordForAdmin(
					jr.get("recordID").getAsInt(), 
					jr.get("state").getAsInt(), 
					jr.get("book").getAsJsonObject().get("title").getAsString(), 
					date, 
					jr.get("user").getAsJsonObject().get("userID").getAsString()
				)
			);
		});
		
		return list;
	}
	
	private static Date convertToDate(String gsondate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("MMM d, yyyy, h:mm:ss a", Locale.ENGLISH).parse(gsondate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return date;
	}
	
	public static ObservableList<Review> reviewsToObservableList(JsonArray reviews) {
		ObservableList<Review> list = FXCollections.observableArrayList();
		reviews.forEach(r -> {
			JsonObject jr = r.getAsJsonObject();
			Set<String> keys = jr.keySet();
			Set<String> k = new HashSet<>(Arrays.asList("reviewID", "user", "book", "review"));
			if(keys.equals(k)) {
				list.add(
					new Review(
						jr.get("reviewID").getAsInt(), 
						jr.get("book").getAsJsonObject().get("bookID").getAsInt(), 
						jr.get("user").getAsJsonObject().get("userID").getAsInt(),
						jr.get("user").getAsJsonObject().get("userName").getAsString(),
						jr.get("review").getAsString() 
					)
				);		
			}
		});
		
		return list;
	}
	
	public static ObservableList<String> reviewsToStringAsObservableList(JsonArray reviews) {
		ObservableList<String> list = FXCollections.observableArrayList();
		reviews.forEach(r -> {
			JsonObject jr = r.getAsJsonObject();
			Set<String> keys = jr.keySet();
			Set<String> k = new HashSet<>(Arrays.asList("reviewID", "user", "book", "review"));
			if(keys.equals(k)) {
				list.add(
					adaptReviewAsString(jr)
				);		
			}
		});
		
		return list;
	}
	
	public static String adaptReviewAsString(JsonObject jr) {
		return jr.get("review").getAsString() + ", by " + 
		jr.get("user").getAsJsonObject().get("userName").getAsString();
	};
	
}
