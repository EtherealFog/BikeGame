package fog.ethereal.world;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import fog.ethereal.sprite.Bike;

public class BikeWorld extends World {
	private Bike bike;
	
	@Override
	public void initialize(Stage primaryStage) {
		// TODO Auto-generated method stub

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
