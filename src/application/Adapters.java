package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Record;

public abstract class Adapters {

	public static ObservableList<Record> recordsToObservableList(JsonArray records, Class<Record> class1) {
		ObservableList<Record> list = FXCollections.observableArrayList();
		records.forEach(r -> {
			JsonObject jr = r.getAsJsonObject();
			Date date = null;
			try {
				date = new SimpleDateFormat("MMM d, yyyy, h:mm:ss a", Locale.ENGLISH).parse(jr.get("date").getAsString());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
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

}
