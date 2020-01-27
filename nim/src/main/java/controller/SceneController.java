package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneController{

	public static Stage primaryStage;
	
	public void changeScene(String fxmlName, String cssName){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
			loader.setController(new SceneController());
			AnchorPane root = (AnchorPane) loader.load();
			
			Scene scene = new Scene(root, 600, 650);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startGame(){
		changeScene("/EnterNames.fxml", "");
	}


}
