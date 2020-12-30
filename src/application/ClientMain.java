package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class ClientMain extends Application {

	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setResizable(false);
			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Biblioteca Demo | Login");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ConnectionSingleton.getInstance(); //create pool
		launch(args);
		ConnectionSingleton.getInstance().shutdown();
	}
}
