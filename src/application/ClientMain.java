package application;
	
import javafx.application.Application;
import javafx.stage.Stage;


public class ClientMain extends Application {

	//TODO create set and get stage, to reuse primaryStage, only change the scene
	
	@Override
	public void start(Stage primaryStage) {
		H.loginWindow(primaryStage, getClass());
	}
	
	public static void main(String[] args) {
		ConnectionSingleton.getInstance(); //create pool
		launch(args);
		ConnectionSingleton.getInstance().shutdown();
	}

}
