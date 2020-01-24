package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewController extends Application{

	@Override
	public void start(Stage primaryStage) {
		try {
			//TODO set the stuff to the main page
			String css ="";
			String fxml = "";
			
			SceneController.primaryStage = primaryStage;
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
			AnchorPane root = (AnchorPane) loader.load();
			
			//TODO set this size
			Scene scene = new Scene(root, 650, 600);
			scene.getStylesheets().add(css);
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
