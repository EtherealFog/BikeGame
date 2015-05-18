package fog.ethereal;

import java.io.IOException;

import fog.ethereal.view.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainClient extends Application {
	
	private AnchorPane rootPane;
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Motor Meander");
		
		initMenu();
	}
	
	public void initMenu() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainClient.class.getResource("view/MenuLayout.fxml"));
			rootPane = (AnchorPane) loader.load();
			
			Scene scene = new Scene(rootPane);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
			
			MenuController controller = loader.getController();
			controller.updateLevels();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
