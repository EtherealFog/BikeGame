package fog.ethereal.view;

import fog.ethereal.util.Constants;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
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
		updateLabels();
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public void setup() {
		accelButton.setOnMouseEntered(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				accelButton.requestFocus();
				accelButton.setText("Press a Key");
				accelButton.setOnKeyPressed(new EventHandler<KeyEvent> () {
					public void handle(KeyEvent e) {
						Constants.ACCEL_CODE = e.getCode();
						updateLabels();
					}
				});
			}
		});
		brakeButton.setOnMouseEntered(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				brakeButton.requestFocus();
				brakeButton.setText("Press a Key");
				brakeButton.setOnKeyPressed(new EventHandler<KeyEvent> () {
					public void handle(KeyEvent e) {
						Constants.BRAKE_CODE = e.getCode();
						updateLabels();
					}
				});
			}
		});
		leftButton.setOnMouseEntered(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				leftButton.requestFocus();
				leftButton.setText("Press a Key");
				leftButton.setOnKeyPressed(new EventHandler<KeyEvent> () {
					public void handle(KeyEvent e) {
						Constants.LEFT_CODE = e.getCode();
						updateLabels();
					}
				});
			}
		});
		rightButton.setOnMouseEntered(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				rightButton.requestFocus();
				rightButton.setText("Press a Key");
				rightButton.setOnKeyPressed(new EventHandler<KeyEvent> () {
					public void handle(KeyEvent e) {
						Constants.RIGHT_CODE = e.getCode();
						updateLabels();
					}
				});
			}
		});
		pauseButton.setOnMouseEntered(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				pauseButton.requestFocus();
				pauseButton.setText("Press a Key");
				pauseButton.setOnKeyPressed(new EventHandler<KeyEvent> () {
					public void handle(KeyEvent e) {
						Constants.PAUSE_CODE = e.getCode();
						updateLabels();
						KeyCode.getKeyCode("Up");
					}
				});
			}
		});
		accelButton.setOnMouseExited(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				updateLabels();
			}
		});
		brakeButton.setOnMouseExited(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				updateLabels();
			}
		});
		leftButton.setOnMouseExited(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				updateLabels();
			}
		});
		rightButton.setOnMouseExited(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				updateLabels();
			}
		});
		pauseButton.setOnMouseExited(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				updateLabels();
			}
		});
	}
	
	public void updateLabels() {
		accelButton.setOnKeyPressed(null);
		accelButton.setText("Accelerate\n(" + Constants.ACCEL_CODE.getName() + ")");
		brakeButton.setOnKeyPressed(null);
		brakeButton.setText("Brake\n(" + Constants.BRAKE_CODE.getName() + ")");
		leftButton.setOnKeyPressed(null);
		leftButton.setText("Rotate Left\n(" + Constants.LEFT_CODE.getName() + ")");
		rightButton.setOnKeyPressed(null);
		rightButton.setText("Rotate Right\n(" + Constants.RIGHT_CODE.getName() + ")");
		pauseButton.setOnKeyPressed(null);
		pauseButton.setText("Play/Pause\n(" + Constants.PAUSE_CODE.getName() + ")");
	}
}
