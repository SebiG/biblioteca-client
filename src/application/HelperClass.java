package application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.google.gson.JsonElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelperClass {

	public static JsonObject buildJsonObj(List<String> list) {
		JsonObject obj = new JsonObject();
		for(int i = 0; i < list.size(); i+=2) {
			obj.addProperty(list.get(i), list.get(i+1));
		}
		return obj;
	}
	public static JsonObject buildJsonObj(String str) {
		Gson gson = new Gson();
		JsonObject obj = gson.fromJson(str, null);
		return obj;
	}
	
	public static void puts(String s) {
		System.out.println(s);
	}
		
	//generic Observable List generator
	public static <T> ObservableList<T> toObservableList(JsonArray serverResponse, Type tClass) {
		ObservableList<T> list = FXCollections.observableArrayList();
		Gson gson = new Gson();
		for(JsonElement jelement: serverResponse) {
	        T obj = gson.fromJson(jelement, tClass);
	        try {
	        	//use reflection to execute initialize method
				Method m = obj.getClass().getMethod("initialize"); 
				m.invoke(obj);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			list.add(obj);
		}
		return list;
	}
	
	public static void loginWindow(Stage primaryStage, Class<? extends ClientMain> class1) {
		try {
			primaryStage.setResizable(false);
			Parent root = FXMLLoader.load(class1.getResource("/view/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(class1.getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Biblioteca Demo | Login");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void logoutUser(ActionEvent e, Button logoutBtn) {
    	Stage logoutStage = (Stage) logoutBtn.getScene().getWindow();
    	logoutStage.close();
        Stage stage = new Stage();
    	HelperClass.loginWindow(stage, ClientMain.class);
	}
}
