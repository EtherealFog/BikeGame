package fog.ethereal.world;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import fog.ethereal.sprite.Bike;
import fog.ethereal.util.Mode;
import fog.ethereal.view.EditorController;

public class BikeWorld extends World {
	private Bike bike;
	
	@Override
	public void initialize(Stage primaryStage, Mode mode) {
		setMode(mode);
		if(getMode().equals(Mode.ADD) || getMode().equals(Mode.MOVE)) {//Edit mode
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(BikeWorld.class.getResource("/src/fog/ethereal/view/EditorLayout.fxml"));
				AnchorPane rootPane = (AnchorPane) loader.load();
				
				EditorController control = loader.getController();
				ScrollPane pane = control.getScrollPane();
		
				Scene scene = new Scene(rootPane);
				primaryStage.setScene(scene);
				primaryStage.show();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(getMode().equals(Mode.PLAY)) {
			
		}
	}

	public void setupKeyMappings(KeyCode accel, KeyCode brake, KeyCode right, KeyCode left) {
		getSurface().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if(e.getCode().equals(accel)) {
					bike.setAccel(true);
					bike.setBrake(false);
				} else if(e.getCode().equals(brake)) {
					bike.setBrake(true);
					bike.setAccel(false);
				} else if(e.getCode().equals(right)) {
					bike.setRight(true);
					bike.setLeft(false);
				} else if(e.getCode().equals(left)) {
					bike.setLeft(true);
					bike.setRight(false);
				}
			}
		});
		
		getSurface().setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if(e.getCode().equals(accel)) {
					bike.setAccel(false);
				} else if(e.getCode().equals(brake)) {
					bike.setBrake(false);
				} else if(e.getCode().equals(right)) {
					bike.setRight(false);
				} else if(e.getCode().equals(left)) {
					bike.setLeft(false);
				}
			}
		});
	}

}
