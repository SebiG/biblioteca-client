package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Book;
import models.Record;
import models.RecordForAdmin;

public abstract class Adapters {
		public static ObservableList<Book> booksToObservableList(JsonArray books) {
		ObservableList<Book> list = FXCollections.observableArrayList();
		books.forEach(r -> {
			JsonObject jb = r.getAsJsonObject();
			list.add(
				new Book(
					jb.get("bookID").getAsInt(), 
					jb.get("author").getAsString(), 
					jb.get("stock").getAsInt(), 
					jb.get("title").getAsString()
					)
			);
		});
		
		return list;
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
}
