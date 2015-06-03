package fog.ethereal.view;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import fog.ethereal.world.BikeWorld;

public class PauseMenuController {
	
	private BikeWorld world;
	private SettingsController sc;
	private ControlSettingsController csc;
	@FXML
	private void initialize() {
		
	}
	
	public void setWorld(BikeWorld world) {
		this.world = world;
	}
	
	@FXML
	public void resume() {
		world.resume();
	}
	
	@FXML
	public void quit() {
		world.quit();
	}
	
	@FXML
	public void showControlSettings() {
		Stage controls = new Stage();
		controls.setTitle("Controls Settings");
		controls.getIcons().add(new Image("file:resources/assets/joystickicon.png"));
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(new URL("file:src/fog/ethereal/view/ControlSettingsLayout.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			
			Scene scene = new Scene(root);
			controls.setScene(scene);
			csc = loader.getController();
			csc.setScene(scene);
			csc.setup();
		} catch (Exception e) {e.printStackTrace();}
		controls.show();
	}
	
	@FXML
	public void showSettings() {
		Stage settings = new Stage();
		settings.setTitle("Settings");
		settings.getIcons().add(new Image("file:resources/assets/settingsicon.png"));
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(new URL("file:src/fog/ethereal/view/SettingsLayout.fxml"));
			BorderPane root = (BorderPane) loader.load();
			
			Scene scene = new Scene(root);
			settings.setScene(scene);
			sc = loader.getController();
		} catch (Exception e) {e.printStackTrace();}
		settings.show();
	}
}
