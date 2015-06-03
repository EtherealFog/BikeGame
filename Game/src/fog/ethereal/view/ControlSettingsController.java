package fog.ethereal.view;

import fog.ethereal.util.Constants;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ControlSettingsController {
	@FXML
	private Button accelButton;
	@FXML
	private Button brakeButton;
	@FXML
	private Button leftButton;
	@FXML
	private Button rightButton;
	@FXML
	private Button pauseButton;
	
	private Scene scene;
	@FXML
	private void initialize() {
		accelButton.setOnMouseEntered(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				scene.setOnKeyPressed(new EventHandler<KeyEvent> () {
					public void handle(KeyEvent e) {
						Constants.ACCEL_CODE = e.getCode();
						accelButton.setText("Accelerate\n(" + e.getCode().getName() + ")");
					}
				});
			}
		});
		brakeButton.setOnMouseEntered(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				scene.setOnKeyPressed(new EventHandler<KeyEvent> () {
					public void handle(KeyEvent e) {
						Constants.BRAKE_CODE = e.getCode();
						accelButton.setText("Brake\n(" + e.getCode().getName() + ")");
					}
				});
			}
		});
		leftButton.setOnMouseEntered(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				scene.setOnKeyPressed(new EventHandler<KeyEvent> () {
					public void handle(KeyEvent e) {
						Constants.LEFT_CODE = e.getCode();
						accelButton.setText("Rotate Left\n(" + e.getCode().getName() + ")");
					}
				});
			}
		});
		rightButton.setOnMouseEntered(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				scene.setOnKeyPressed(new EventHandler<KeyEvent> () {
					public void handle(KeyEvent e) {
						Constants.RIGHT_CODE = e.getCode();
						accelButton.setText("Rotate Right\n(" + e.getCode().getName() + ")");
					}
				});
			}
		});
		pauseButton.setOnMouseEntered(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				scene.setOnKeyPressed(new EventHandler<KeyEvent> () {
					public void handle(KeyEvent e) {
						Constants.PAUSE_CODE = e.getCode();
						accelButton.setText("Play/Pause\n(" + e.getCode().getName() + ")");
					}
				});
			}
		});
		accelButton.setOnMouseExited(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				scene.setOnKeyPressed(null);
			}
		});
		brakeButton.setOnMouseExited(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				scene.setOnKeyPressed(null);
			}
		});
		leftButton.setOnMouseExited(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				scene.setOnKeyPressed(null);
			}
		});
		rightButton.setOnMouseExited(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				scene.setOnKeyPressed(null);
			}
		});
		pauseButton.setOnMouseExited(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				scene.setOnKeyPressed(null);
			}
		});
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
}
