package application;

import java.util.List;

import com.google.gson.JsonObject;

public class HelperClass {

	public static JsonObject buildJsonObj(List<String> list) {
		JsonObject obj = new JsonObject();
		for(int i = 0; i < list.size(); i+=2) {
			obj.addProperty(list.get(i), list.get(i+1));
		}
		return obj;
	}
}
