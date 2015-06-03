package fog.ethereal.view;

import java.io.IOException;

import fog.ethereal.client.MainClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RenameDialogController {
	
	@FXML
	private TextField nameField;
	
	@FXML
	private BorderPane rootPane;
	
	private MenuController parent;
	
	private Stage stage;
	
	public RenameDialogController() {
		
	}
	
	@FXML
	public void rename() {
		parent.renameCurrent(nameField.getText());
		stage.hide();
	}
	
	public void show() {
		nameField.setText("");
		stage.show();
	}
	
	public void setup(MenuController parent, Stage stage) {
		this.parent = parent;
		this.stage = stage;
	}
}
