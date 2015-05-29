package fog.ethereal.view;

import fog.ethereal.util.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SettingsController {
	@FXML
	private Slider gravSlider;
	@FXML
	private Slider accelSlider;
	@FXML
	private Slider speedSlider;
	@FXML
	private TextField gravField;
	@FXML
	private TextField accelField;
	@FXML
	private TextField speedField;
	
	@FXML
	private void initialize() {
		gravSlider.setOnMouseDragged(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				double value = gravSlider.getValue();
				Constants.GRAVITY = value;
				gravField.setText("" + round(value));
			}
		});
		accelSlider.setOnMouseDragged(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				double value = accelSlider.getValue();
				Constants.ACCEL = value;
				accelField.setText(""+ round(value));
			}
		});
		speedSlider.setOnMouseDragged(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent e) {
				double value = speedSlider.getValue();
				Constants.SPEED = value;
				speedField.setText("" + round(value, 2));
			}
		});
		gravField.setOnAction(new EventHandler<ActionEvent> () {
			public void handle(ActionEvent e) {
				double value = Double.parseDouble(gravField.getText());
				gravSlider.setValue(value);
				Constants.GRAVITY = value;
			}
		});
		accelField.setOnAction(new EventHandler<ActionEvent> () {
			public void handle(ActionEvent e) {
				double value = Double.parseDouble(accelField.getText());
				accelSlider.setValue(value);
				Constants.ACCEL = value;
			}
		});
		speedField.setOnAction(new EventHandler<ActionEvent> () {
			public void handle(ActionEvent e) {
				double value = Double.parseDouble(speedField.getText());
				speedSlider.setValue(value);
				Constants.SPEED = value;
			}
		});
	}
	
	public double round(double value , int digits) {
		return Math.round(value * Math.pow(10, digits)) / Math.pow(10, digits);
	}
	
	public double round(double value) {
		return round(value, 1);
	}
	
	@FXML
	private void resetGravity() {
		gravSlider.setValue(Constants.DEFAULT_GRAVITY);
		gravField.setText("" + Constants.DEFAULT_GRAVITY);
	}
	
	@FXML
	private void resetAccel() {
		accelSlider.setValue(Constants.DEFAULT_ACCEL);
		accelField.setText("" + Constants.DEFAULT_ACCEL);
	}
	
	@FXML
	private void resetSpeed() {
		speedSlider.setValue(Constants.DEFAULT_SPEED);
		speedField.setText("" + Constants.DEFAULT_SPEED);
	}
}
