package fog.ethereal.view;

import java.awt.Point;

import fog.ethereal.util.LevelSaver;
import fog.ethereal.world.Level;
import fog.ethereal.world.Section;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewDialogController {
	@FXML
	private TextField input;
	
	private MenuController parent;
	private Stage window;
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	public void create() {
		Level temp = new Level(input.getText());
		temp.addSection(new Section(new Point(200, 300), new Point(800, 300)));
		LevelSaver.save(temp.toSaveableLevel());
		window.hide();
	}
	
	@FXML
	public void cancel() {
		window.hide();
	}
	
	public void show() {
		input.setText("");
		window.show();
	}
	
	public void setup(MenuController parent, Stage window) {
		this.parent = parent;
		this.window = window;
	}
}
