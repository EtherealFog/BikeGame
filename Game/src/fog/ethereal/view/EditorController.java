package fog.ethereal.view;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import fog.ethereal.util.Mode;

public class EditorController {
	@FXML
	private ToggleGroup modes;
	
	@FXML
	private ScrollPane scene;
	
	private Mode mode;
	
	@FXML
	public void setMove() {
		mode = Mode.MOVE;
	}
	
	@FXML
	public void setAdd() {
		mode = Mode.ADD;
	}
	
	public ScrollPane getScene() {
		return scene;
	}
	
	public Mode getMode() {
		return mode;
	}
}
