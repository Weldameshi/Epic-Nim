package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewController extends Application{
	public static GameController gc;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			gc = new GameController();
			SceneController.primaryStage = primaryStage;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
			loader.setController(gc);
			AnchorPane root = (AnchorPane) loader.load();
			
			Scene scene = new Scene(root, 600, 650);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
