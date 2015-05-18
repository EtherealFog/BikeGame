package fog.ethereal.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RenameDialogController {
	
	@FXML
	private TextField nameField;
	
	private MenuController parent;
	
	public RenameDialogController(MenuController parent) {
		this.parent = parent;
	}
	
	@FXML
	public void rename() {
		parent.renameCurrent(nameField.getText());
	}
}
